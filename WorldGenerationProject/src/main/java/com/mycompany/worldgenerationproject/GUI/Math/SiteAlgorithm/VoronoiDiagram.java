/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worldgenerationproject.GUI.Math.SiteAlgorithm;

import com.mycompany.worldgenerationproject.GUI.Math.Vector2;
import java.util.ArrayList;
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
    
    
    
    
    
    
    public VoronoiDiagram(VoronoiModel model)
    {
        Vector2 v = model.getDimensions();
        bounds = new Rect(0, v.getX(), 0, v.getY());
        
        List<Vector2> sites = model.getSites();
        cells = new ArrayList<Cell>();
        
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
    public void add(Cell c)
    {
        // Runs for each cell that already exists
        for (Cell cell : cells)
        {
            
            // The vector from the new cell's site to the current cell's site
            Vector2 between = Vector2.sub(cell.getSite(), c.getSite());
            // Finds the midpoint between the two cells
            Vector2 mid = Vector2.add(c.getSite(), between.div(2));
            
            // If there is only one other cell, no edges exist yet
            // This starts everything off
            if (cells.size() == 1)
            {
                // Finds the first edge
                Line perLine = Line.perLine( new Line(mid, between.normalize()) );
                // Bounds the edge to the diagram
                perLine.bound(bounds);
                
                // Adds the new edge to the new cell
                c.addEdge(perLine);
                // Adds the new edge to the preexisting cell
                cell.addEdge(perLine);
            }
            else
            {
                
                // The line that is perpendicular to the line between the new site and current site
                //Line perLine = null;
                // Creating the new edge
                Line perLine = Line.perLine( new Line(mid, between.normalize()) );
                perLine.bound(bounds);
                
                // While there are edges to be looked at
                for (Line e : cell.getEdges())
                {
                    /*
                    // The vector from the start to the site
                    Vector2 siteVec = Vector2.sub(cell.getSite(), e.getStart()).normalize();
                    // The vector from the start to the midpoint
                    Vector2 midVec = Vector2.sub (mid           , e.getStart()).normalize();
                    // The vector from the start to the end
                    Vector2 edgeVec = Vector2.add(e.getEnd()    , e.getStart());

                    // The cross product of the site vector as compared to the edge vector
                    double spatialSite = edgeVec.cross(siteVec);
                    // The cross product of the midpoint vector as opposed to the edge vector
                    double spatialMid = edgeVec.cross(midVec);
                    
                    //System.out.println(spatialSite);
                    //System.out.println(spatialMid);

                    // If both of the cross-products are on the same side
                    if (spatialSite < 0 && spatialMid < 0)
                    {
                        // Creating the new edge
                        perLine = Line.perLine( new Line(mid, between.normalize()) );
                        // Bounding this line into the diagram
                        perLine.bound(bounds);
                        
                        
                        // If the midpoint vector is between the edge vector and
                        // the site vector
                        if (spatialSite >= spatialMid)
                        {
                            // Finds the intersection of the perpendicular line and this edge
                            Vector2 intersection = perLine.intersect(e);
                            
                            //System.out.println(intersection);

                            // If the intersection exists (i.e. they aren't parallel)
                            if (intersection != null)
                            {
                                // Finds the vector from the center of the line to start
                                Vector2 midpointToEStart = Vector2.sub(e.getStart(), e.getA());
                                // Finds the vector from the the center of the line to the intersection
                                Vector2 midpointToInter  = Vector2.sub(intersection, e.getA());

                                // Finding if the intersection is on the start side or end side of line
                                // Runs if on the start side
                                if (compareDirections(midpointToEStart, midpointToInter))
                                {
                                    // If the intersection is closer to the midpoint than the start
                                    if (intersection.dist(e.getA()) < e.getStart().dist(e.getA()))
                                        e.setStart(intersection);
                                }
                                // Runs if it is on the end side
                                else
                                {
                                    // If the intersection is closer to the midpoint than the end
                                    if (intersection.dist(e.getA()) < e.getEnd().dist(e.getA()))
                                        e.setEnd(intersection);
                                }

                                // Finds the vector between the midpoint of the new edge and its start
                                Vector2 sourceToStart = Vector2.sub(perLine.getStart(), perLine.getA());
                                // Finds the vector between the midpoint of the new edge and its end
                                Vector2 sourceToInter = Vector2.sub(intersection, perLine.getA());

                                // Finding if the intersection is on the start side or end side of line
                                // If the intersection is on the same side as the start
                                if (compareDirections(sourceToStart, sourceToInter))
                                {
                                    // If the intersection is closer to the new edge's midpoint than the start
                                    if (intersection.dist(perLine.getA()) < perLine.getStart().dist(perLine.getA()))
                                        perLine.setStart(intersection);
                                }
                                // If the intersection is on the same side as the start
                                else
                                {
                                    // If the intersection is closer to the new edge's midpoint than the end
                                    if (intersection.dist(perLine.getA()) < perLine.getEnd().dist(perLine.getA()))
                                        perLine.setEnd(intersection);
                                }
                            }
                        }
                    }
                    // If both of the cross-products are on the same side
                    else if (spatialSite >= 0 && spatialMid >= 0)
                    {
                        // Creating the new edge
                        perLine = Line.perLine( new Line(mid, between.normalize()) );
                        // Bounding this line into the diagram
                        perLine.bound(bounds);

                        // If the midpoint vector is between the edge vector and
                        // the site vector
                        if (spatialSite <= spatialMid)
                        {
                            // Finds the intersection of the perpendicular line and this edge
                            Vector2 intersection = perLine.intersect(e);
                            
                            //System.out.println(intersection);

                            // If the intersection exists (i.e. they aren't parallel)
                            if (intersection != null)
                            {
                                // Finds the vector from the center of the line to start
                                Vector2 midpointToEStart = Vector2.sub(e.getStart(), e.getA());
                                // Finds the vector from the the center of the line to the intersection
                                Vector2 midpointToInter  = Vector2.sub(intersection, e.getA());

                                // Finding if the intersection is on the start side or end side of line
                                // Runs if on the start side
                                if (compareDirections(midpointToEStart, midpointToInter))
                                {
                                    // If the intersection is closer to the midpoint than the start
                                    if (intersection.dist(e.getA()) < e.getStart().dist(e.getA()))
                                        e.setStart(intersection);
                                }
                                // Runs if it is on the end side
                                else
                                {
                                    // If the intersection is closer to the midpoint than the end
                                    if (intersection.dist(e.getA()) < e.getEnd().dist(e.getA()))
                                        e.setEnd(intersection);
                                }

                                // Finds the vector between the midpoint of the new edge and its start
                                Vector2 sourceToStart = Vector2.sub(perLine.getStart(), perLine.getA());
                                // Finds the vector between the midpoint of the new edge and its end
                                Vector2 sourceToInter = Vector2.sub(intersection, perLine.getA());

                                // Finding if the intersection is on the start side or end side of line
                                // If the intersection is on the same side as the start
                                if (compareDirections(sourceToStart, sourceToInter))
                                {
                                    // If the intersection is closer to the new edge's midpoint than the start
                                    if (intersection.dist(perLine.getA()) < perLine.getStart().dist(perLine.getA()))
                                        perLine.setStart(intersection);
                                }
                                // If the intersection is on the same side as the start
                                else
                                {
                                    // If the intersection is closer to the new edge's midpoint than the end
                                    if (intersection.dist(perLine.getA()) < perLine.getEnd().dist(perLine.getA()))
                                        perLine.setEnd(intersection);
                                }
                            }
                        }
                    }*/
                }
                /*
                if (perLine != null)
                {*/
                    // Adding the new edge to c (the new cell)
                    c.addEdge(perLine);
                    // Adding the new edge to cell
                    cell.addEdge(perLine);
                /*}*/
            }
            
            // NOTE: CONSIDER THE CASE WHERE THE NEW EDGE IS NOT INCLUDED
            // FIND PROPERTIES OF THIS
            // MAY BE COVERED
            
        }
        
        // Adding the new cell
        cells.add(c);
        
        // Removes floating edges
        //cleanEdges();
        
    }
    
    // Possibly unnecessary
    private void cleanEdges()
    {
        for (Cell c : cells)
        {
            List<Integer> indeces = new ArrayList<Integer>();
            
            for (Line e : c.getEdges())
            {
                Vector2 start = e.getStart();
                Vector2 end = e.getEnd();
                
                for (Line edge : c.getEdges())
                {
                    if (e != edge)
                    {
                        
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
