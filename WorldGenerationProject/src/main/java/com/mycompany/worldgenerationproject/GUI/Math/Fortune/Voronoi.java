/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worldgenerationproject.GUI.Math.Fortune;

import com.mycompany.worldgenerationproject.GUI.Math.Vector2;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author User
 */
public class Voronoi
{
    
    List<Vector2> places;
    List<VEdge> edges;
    double width, height;
    VParabola root;
    double ly;
    
    Set<VEvent> deleted;
    List<Vector2> points;
    PriorityQueue<VEvent> queue;
    
    
    
    
    
    
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
    
    public void setEdges(List<VEdge> edges)
    {
        this.edges = edges;
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
    
    
    
    
    
    
    public List<VEdge> getEdges(List<Vector2> sites, int w, int h)
    {
        
        places = sites;
        width = w;
        height = h;
        root = null;
        
        edges = new ArrayList<VEdge>();
        queue = new PriorityQueue<VEvent>(places.size(), new Comparator()
        {
            @Override
            public int compare(Object o1, Object o2)
            {
                VEvent e1 = (VEvent)o1;
                VEvent e2 = (VEvent)o2;
                
                return e1.compareTo(e2);
            }
        });
        
        for (Vector2 site : places)
        {
            queue.add(new VEvent(site, true));
        }
        
        
        
        VEvent e;
        while (!queue.isEmpty())
        {
            e = queue.poll();
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
        
        Iterator<VEdge> iter = edges.iterator();
        while(iter.hasNext())
        {
            VEdge edge = iter.next();
            
            if (edge.getNeighbor() != null)
            {
                edge.setStart(edge.getNeighbor().getEnd());
                edge.setNeighbor(null);
            }
        }
        
        
        
        return edges;
        
    }

    private void InsertParabola(Vector2 point)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void RemoveParabola(VEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void FinishEdge(VParabola root)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
