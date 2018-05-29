/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worldgenerationproject.GUI.Math.Fortune;

import com.mycompany.worldgenerationproject.GUI.Math.Vector2;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author User
 */
public class Voronoi
{
    
    private List<Vector2> places;
    private List<VEdge> edges;
    private double width, height;
    private VParabola root;
    private double ly;
    
    private Set<VEvent> deleted;
    private List<Vector2> points;
    private PriorityQueue<VEvent> queue;
    
    
    
    
    
    
    public Voronoi()
    {
        edges = null;
    }
    
    
    
    
    
    
    public List<Vector2> getPlaces()
    {
        return places;
    }
    
    public void setPlaces(List<Vector2> places)
    {
        this.places = places;
    }
    
    public List<VEdge> getEdges()
    {
        return edges;
    }
    
    public double getWidth()
    {
        return width;
    }
    
    public void setWidth(double width)
    {
        this.width = width;
    }
    
    public double getHeight()
    {
        return height;
    }
    
    public void setHeight(double height)
    {
        this.height = height;
    }
    
    public VParabola getRoot()
    {
        return root;
    }
    
    public void setRoot(VParabola root)
    {
        this.root = root;
    }
    
    public double getLy() 
    {
        return ly;
    }
    
    public void setLy(double ly)
    {
        this.ly = ly;
    }
    
    public Set<VEvent> getDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(Set<VEvent> deleted)
    {
        this.deleted = deleted;
    }
    
    public List<Vector2> getPoints()
    {
        return points;
    }
    
    public void setPoints(List<Vector2> points)
    {
        this.points = points;
    }
    
    public PriorityQueue<VEvent> getQueue()
    {
        return queue;
    }
    
    public void setQueue(PriorityQueue<VEvent> queue)
    {
        this.queue = queue;
    }
    
    
    
    
    
    
    public List<VEdge> buildEdges(List<Vector2> sites, int w, int h)
    {
        
        places = sites;
        width = w;
        height = h;
        root = null;
        deleted = new TreeSet<VEvent>();
        points = new ArrayList<Vector2>();
        
        edges = new ArrayList<VEdge>();
        queue = new PriorityQueue<VEvent>(places.size());
        
        for (Vector2 site : places)
        {
            queue.add(new VEvent(site, true));
        }
        
        
        
        while (!queue.isEmpty())
        {
            VEvent e = queue.remove();
            ly = e.getPoint().getY();
            
            if (deleted.contains(e))
            {
                deleted.remove(e);
                continue;
            }
            
            if (e.isPe())
                InsertParabola(e.getPoint());
            else
                RemoveParabola(e);
        }
        
        FinishEdge(root);
        
        for (VEdge e : edges)
        {
            if (e.getNeighbor() != null)
            {
                e.setStart(e.getNeighbor().getEnd());
                e.setNeighbor(null);
            }
        }
        
        return edges;
        
    }

    private void InsertParabola(Vector2 p)
    {
        
        if (root == null)
        {
            root = new VParabola(p);
            return;
        }
        
        /*
        if (root.isLeaf() && root.getSite().getY() - p.getY() < 1)
        {
            Vector2 fp = root.getSite();
            
            root.setLeaf(false);
            root.setLeft( new VParabola (fp) );
            root.setRight(new VParabola (p) );
            
            Vector2 s = new Vector2((p.getX() + fp.getX())/2, height);
            
            points.add(s);
            if (p.getX() > fp.getX())
                root.setEdge( new VEdge(s, fp, p) );
            else
                root.setEdge( new VEdge(s, p, fp) );
            edges.add(root.getEdge());
            
            return;
        }
        */
        
        VParabola par = GetParabolaByX(p.getX());
        
        if (par.getcEvent() != null)
        {
            deleted.add(par.getcEvent());
            par.setcEvent(null);
        }
        
        Vector2 start = new Vector2(p.getX(), getY(par.getSite(), p.getX()));
        points.add(start);
        
        VEdge el = new VEdge(start, par.getSite(), p);
        VEdge er = new VEdge(start, p, par.getSite());
        
        el.setNeighbor(er);
        er.setNeighbor(el);
        par.setEdge(el);
        par.setLeaf(false);
        
        VParabola p0 = new VParabola(par.getSite());
        VParabola p1 = new VParabola(p);
        VParabola p2 = new VParabola(par.getSite());
        
        par.setLeft(p0);
        par.setRight(new VParabola());
        par.getRight().setEdge(er);
        par.getRight().setLeft(p1);
        par.getRight().setRight(p2);
        
        checkCircle(p0);
        checkCircle(p2);
        
    }

    private void RemoveParabola(VEvent e)
    {
        
        VParabola p1 = e.getArch();
        
        VParabola xl = VParabola.getLeftParent(p1);
        VParabola xr = VParabola.getRightParent(p1);
        
        VParabola p0 = VParabola.getLeftChild(xl);
        VParabola p2 = VParabola.getRightChild(xr);
        
        if (p0.getcEvent() != null)
        {
            queue.remove(p0.getcEvent());
            p0.setcEvent(null);
        }
        if (p2.getcEvent() != null)
        {
            queue.remove(p2.getcEvent());
            p2.setcEvent(null);
        }
        
        if (p0 == p2)
            System.out.println("Error: left and right parabolas have same focus.");
        
        Vector2 p = new Vector2(e.getPoint().getX(), getY(p1.getSite(), e.getPoint().getX()));
        points.add(p);
        
        xl.getEdge().setEnd(p);
        xr.getEdge().setEnd(p);
        edges.add(xl.getEdge());
        edges.add(xr.getEdge());
        
        VParabola higher = new VParabola();
        VParabola par = p1;
        while (par != root)
        {
            par = par.getParent();
            if (par == xl)
                higher = xl;
            if (par == xr)
                higher = xr;
        }
        higher.setEdge(new VEdge(p, p0.getSite(), p2.getSite()));
        
        VParabola gparent = p1.getParent().getParent();
        if (p1.getParent().getLeft() == p1)
        {
            if (gparent.getLeft() == p1.getParent())
                gparent.setLeft(p1.getParent().getRight());
            if (gparent.getRight() == p1.getParent())
                gparent.setRight(p1.getParent().getRight());
        }
        else
        {
            if (gparent.getLeft() == p1.getParent())
                gparent.setLeft(p1.getParent().getLeft());
            if (gparent.getRight() == p1.getParent())
                gparent.setRight(p1.getParent().getLeft());
        }
        
        //p1.setParent(null);
        
        checkCircle(p0);
        checkCircle(p2);
        
    }

    private void FinishEdge(VParabola n)
    {
        
        if (n.isLeaf())
            return;
        
        // Fix using reference Voronoi-master
        double mx = getXOfEdge(n, ly);
        
        Vector2 end = new Vector2(mx, mx * n.getEdge().getF() + n.getEdge().getG());
        n.getEdge().setEnd(end);
        edges.add(n.getEdge());
        points.add(end);
        
        FinishEdge(n.getLeft());
        FinishEdge(n.getRight());
        
    }
    
    private double getXOfEdge(VParabola par, double y)
    {
        
        VParabola left = VParabola.getLeftChild(par);
        VParabola right = VParabola.getRightChild(par);
        
        Vector2 p = left.getSite();
        Vector2 r = right.getSite();
        
        double dp = 2.0 * (p.getY() - y);
        double a1 = 1.0 / dp;
        double b1 = -2.0 * p.getX() / dp;
        double c1 = y + dp / 4 + p.getX() * p.getX() / dp;
        
        dp = 2.0 * (r.getY() - y);
        double a2 = 1.0 / dp;
        double b2 = -2.0 * r.getX() / dp;
        double c2 = ly + dp / 4 + r.getX() * r.getX() / dp;
        
        double a = a1 - a2;
        double b = b1 - b2;
        double c = c1 - c2;
        
        double disc = b*b - 4 * a * c;
        double x1 = (-b + Math.sqrt(disc)) / (2 * a);
        double x2 = (-b - Math.sqrt(disc)) / (2 * a);
        
        double ry;
        if (p.getY() < r.getY())
            ry = Math.max(x1, x2);
        else
            ry = Math.min(x1, x2);
        
        return ry;
        
    }

    private VParabola GetParabolaByX(double xx)
    {
        
        VParabola par = root;
        double x = 0.0;
        
        while (!par.isLeaf())
        {
            x = getXOfEdge(par, ly);
            if (x > xx)
                par = par.getLeft();
            else
                par = par.getRight();
        }
        
        return par;
        
    }

    private double getY(Vector2 p, double x)
    {
        double dp = 2 * (p.getY() - ly);
	double a1 = 1 / dp;
	double b1 = -2 * p.getX() / dp;
	double c1 = ly + dp / 4 + p.getX() * p.getX() / dp;
	
	return(a1*x*x + b1*x + c1);
    }

    private void checkCircle(VParabola b)
    {
        
        VParabola lp = VParabola.getLeftParent(b);
        VParabola rp = VParabola.getRightParent(b);
        
        VParabola a = VParabola.getLeftChild(lp);
        VParabola c = VParabola.getRightChild(rp);
        
        if (a == null
                || c == null
                || a.getSite() == c.getSite())
            return;
        
        Vector2 s;
        s = getEdgeIntersection(lp.getEdge(), rp.getEdge());
        if (s == null) return;
        
        double dx = a.getSite().getX() - s.getX();
        double dy = a.getSite().getY() - s.getY();
        
        double d = Math.sqrt( (dx*dx) + (dy*dy) );
        
        if (s.getY() - d >= ly)
            return;
        
        VEvent e = new VEvent( new Vector2(s.getX(), s.getY() - d), false);
        points.add(e.getPoint());
        b.setcEvent(e);
        e.setArch(b);
        queue.add(e);
        
    }
    
    private Vector2 getEdgeIntersection(VEdge a, VEdge b)
    {
        
        double x = (b.getG() - a.getG()) / (a.getF() - b.getF());
        double y = a.getF() * x + a.getG();
        
        if ( (x - a.getStart().getX()) / a.getDirection().getX() < 0)
            return null;
        if ( (y - a.getStart().getY()) / a.getDirection().getY() < 0)
            return null;
        
        if ( (x - b.getStart().getX()) / b.getDirection().getX() < 0)
            return null;
        if ( (y - b.getStart().getY()) / b.getDirection().getY() < 0)
            return null;
        
        Vector2 p = new Vector2(x, y);
        points.add(p);
        return p;
        
    }
    
}
