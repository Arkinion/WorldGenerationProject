/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worldgenerationproject.GUI.Math.DelaunayAlgorithm;

import com.mycompany.worldgenerationproject.GUI.Math.Vector2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author rose_880118
 */
public class VoronoiDiagram
{
    
    private Rect bounds;
    private List<Cell> cells;
    private List<Circumcenter> centers;
    
    
    
    
    
    
    public VoronoiDiagram(VoronoiModel model)
    {
        Vector2 v = model.getDimensions();
        bounds = new Rect(0, v.getX(), 0, v.getY());
        
        HashSet<Vector2> sites = model.getSites();
        cells = new ArrayList<Cell>();
        centers = new ArrayList<Circumcenter>();
        
        for (Vector2 s : sites)
        {
            add(new Cell(s));
        }
    }
    
    
    
    
    
    
    public List<Cell> getCells()
    {
        return cells;
    }
    
    public void setCells(List<Cell> cells)
    {
        this.cells = cells;
    }
    
    public Rect getBounds()
    {
        return bounds;
    }
    
    public void setBounds(Rect bounds)
    {
        this.bounds = bounds;
    }
    
    
    
    
    
    // Adds a new Cell to the diagram
    public void add(Cell c1)
    {
        centers = new ArrayList<Circumcenter>();
        for (Cell c : cells)
        {
            c.cleanCircs(c1.getSite());
            for (Circumcenter cent : c.getCircs())
            {
                if (!centers.contains(cent))
                    centers.add(cent);
            }
        }
        
        for (int i = 0; i < cells.size(); i++)
        {
            Cell c2 = cells.get(i);
            
            // The vector from the new cell's site to the current cell's site
            Vector2 between2 = Vector2.sub(c2.getSite(), c1.getSite());
            // Finds the midpoint between the two cells
            Vector2 mid2 = Vector2.add(c1.getSite(), between2.div(2));
            
            if (cells.size() != 1)
            {
                for (int j = 0; j < cells.size(); j++)
                {
                    Cell c3 = cells.get(j);
                    
                    if (c2 != c3)
                    {
                        // The vector from the new cell's site to the current cell's site
                        Vector2 between3 = Vector2.sub(c3.getSite(), c1.getSite());
                        // Finds the midpoint between the two cells
                        Vector2 mid3 = Vector2.add(c1.getSite(), between3.div(2));
                        
                        Line midLine2 = Line.perLine( new Line(mid2, between2.normalize()) );
                        Line midLine3 = Line.perLine( new Line(mid3, between3.normalize()) );
                        
                        /*
                        midLine1.bound(bounds);
                        midLine2.bound(bounds);
                        midLine3.bound(bounds);
                        c1.addEdge(midLine1);
                        c1.addEdge(midLine2);
                        c1.addEdge(midLine3);
                        c2.addEdge(midLine1);
                        c2.addEdge(midLine2);
                        c2.addEdge(midLine3);
                        c3.addEdge(midLine1);
                        c3.addEdge(midLine2);
                        c3.addEdge(midLine3);
                        
                        Vector2 end = midLine2.intersect(midLine3);
                        System.out.println(end);
                        */

                        Vector2 end = midLine2.intersect(midLine3);
                        //System.out.println(end);
                        if (end != null)
                        {
                            double radius = end.dist(c1.getSite());
                            //System.out.println(radius);

                            for (Cell c : cells)
                            {
                                if (c != c1 && end.dist(c.getSite()) < radius)
                                {
                                    end = null;
                                    break;
                                }
                            }

                            if (end != null)
                            {
                                Circumcenter newCenter = new Circumcenter(end);
                                newCenter.addPart(c1);
                                newCenter.addPart(c2);
                                newCenter.addPart(c3);
                                if (!centers.contains(end))
                                    centers.add(newCenter);
                                c1.addCirc(newCenter);
                                c2.addCirc(newCenter);
                                c3.addCirc(newCenter);
                                //System.out.println(newCenter);
                            }
                        }
                    }
                }
            }
            else
            {
                Line newEdge = Line.perLine( new Line(mid2, between2.normalize()) );
                newEdge.bound(bounds);
                c1.addEdge( newEdge );
                c2.addEdge( newEdge );
                cells.add(c1);
                return;
            }
        }
        
        // Adding the new cell
        cells.add(c1);
        
        if (cells.size() > 2)
        {
            buildEdges();
        }
        
        /*System.out.println("BEGIN");
        for (Circumcenter c : centers)
        {
            System.out.println(c.getCenter() + " :::: " + c.getParts());
        }*/
    }
    
    private void buildEdges()
    {
        for (Cell c : cells)
        {
            c.setEdges(new ArrayList<Line>());
        }
        
        
        for (int i = 0; i < cells.size(); i++)
        {
            Cell c1 = cells.get(i);
            
            for (int j = 0; j < cells.size(); j++)
            {
                Cell c2 = cells.get(j);
                
                if (c1 != c2)
                {
                    // The vector from the new cell's site to the current cell's site
                    Vector2 between2 = Vector2.sub(c2.getSite(), c1.getSite());
                    // Finds the midpoint between the two cells
                    Vector2 mid2 = Vector2.add(c1.getSite(), between2.div(2));
                    
                    Line midLine = Line.perLine( new Line (mid2, between2.normalize() ));
                    
                    for (Circumcenter circ1 : c1.getCircs())
                    {
                        Vector2 circ1Cent = circ1.getCenter();
                        if (midLine.onLine(circ1Cent))
                        {
                            //System.out.println(i + "CIRC1 ON LINE : " + circ1);
                            for (Circumcenter circ2 : c1.getCircs())
                            {
                                Vector2 circ2Cent = circ2.getCenter();
                                
                                if (!circ1.equals(circ2) && midLine.onLine(mid2) /*midLine.parallel( new Line (circ1.getCenter(), Vector2.sub(circ2.getCenter(), circ1.getCenter()).normalize()))*/)
                                {
                                    midLine.setStart(circ1Cent);
                                    midLine.setEnd(circ2Cent);
                                    c1.addEdge(midLine);
                                }
                            }
                            
                            boolean infinite = midLine.getEnd() == null;
                            
                            if (infinite)
                            {
                                //System.out.println("RAN");
                                for (Circumcenter cent : centers)
                                {
                                    if (!circ1.equals(cent) && midLine.onLine(cent.getCenter()))
                                    {
                                        infinite = false;
                                        //System.out.println("ONLINE");
                                    }
                                }
                                
                                if (infinite)
                                {
                                    Cell c3 = null;
                                    
                                    for (Cell c : circ1.getParts())
                                    {
                                        if (c != c1 && c != c2)
                                            c3 = c;
                                    }
                                    
                                    midLine.bound(bounds);
                                    Line tester = new Line(circ1.getCenter(), between2.normalize());
                                    tester.bound(bounds);
                                    
                                    boolean sideC3 = tester.relativity(c3.getSite()) < 0;
                                    boolean sideStart = tester.relativity(midLine.getStart()) < 0;
                                    
                                    //System.out.println(cells.size() + " " + sideC3 + "," + sideStart);
                                    
                                    if (sideC3 == sideStart)
                                    {
                                        //System.out.println(midLine + " , " + circ1.getCenter());
                                        midLine.setStart(circ1.getCenter());
                                    }
                                    else
                                    {
                                        midLine.setEnd(circ1.getCenter());
                                    }
                                    
                                    c1.addEdge(midLine);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    // Returns true if the vectors are both in the same quadrant.
    private boolean compareDirections(Vector2 x, Vector2 y)
    {
        if (x.getX() < 0 && y.getX() < 0)
        {
            if (x.getY() < 0 && y.getY() < 0)
                return true;
            else if (x.getY() >= 0 && y.getY() >= 0)
                return true;
        }
        else if (x.getX() >= 0 && y.getX() >= 0)
        {
            if (x.getY() < 0 && y.getY() < 0)
                return true;
            else if (x.getY() >= 0 && y.getY() >= 0)
                return true;
        }
        return false;
    }
    
    public boolean contains(Cell c)
    {
        for (Cell cell : cells)
        {
            if (cell.getSite().equals(c.getSite()))
                return true;
        }
        return false;
    }
    
}
