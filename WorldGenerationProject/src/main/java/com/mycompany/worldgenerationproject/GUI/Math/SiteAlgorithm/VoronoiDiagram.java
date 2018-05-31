/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worldgenerationproject.GUI.Math.SiteAlgorithm;

import com.mycompany.worldgenerationproject.GUI.Math.Vector2;
import java.util.ArrayList;
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
            
            // The points that exist on each side of the new edge
            ArrayList<Vector2> startSide = new ArrayList<Vector2>();
            ArrayList<Vector2> endSide = new ArrayList<Vector2>();

            // For all of the edges on the cell being looked at
            for (Line e : cell.getEdges())
            {
                // The vector from the start to the site
                Vector2 siteVec = Vector2.sub(cell.getSite(), e.getStart()).normalize();
                // The vector from the start to the midpoint
                Vector2 midVec = Vector2.sub (mid           , e.getStart()).normalize();
                // The vector from the start to the end
                Vector2 edgeVec = Vector2.add(e.getEnd()    , e.getStart()).normalize();
                
                // The cross product of the site vector as compared to the edge vector
                double spatialSite = edgeVec.cross(siteVec);
                // The cross product of the midpoint vector as opposed to the edge vector
                double spatialMid = edgeVec.cross(midVec);
                
                // The line that is perpendicular to the line between the new site and current site
                Line perLine = Line.perLine( new Line(mid, between.normalize()) );
                // Bounding this line into the diagram
                perLine.bound(bounds);
                
                // If both of the cross-products are on the same side
                if (spatialSite < 0 && spatialMid < 0)
                {
                    // If the midpoint vector is between the edge vector and
                    // the site vector
                    if (spatialSite <= spatialMid)
                    {
                        // Finds the intersection of the perpendicular line and this edge
                        Vector2 intersection = perLine.intersect(e);
                        
                        // If the intersection exists (i.e. they aren't parallel)
                        if (intersection != null)
                        {
                            // Finds the vector from the center of the line to start
                            Vector2 midpointToEStart = Vector2.sub(e.getStart(), e.getA());
                            // Finds the vector from the the center of the line to the intersection
                            Vector2 midpointToInter  = Vector2.sub(intersection, e.getA());
                            
                            // COMPARE TO MIDPOINT, NOT SITE
                            if (compareDirections(midpointToEStart, midpointToInter))
                            {
                                if (intersection.dist(cell.getSite()) < e.getStart().dist(cell.getSite()))
                                    e.setStart(intersection);
                            }
                            else
                            {
                                if (intersection.dist(cell.getSite()) < e.getEnd().dist(cell.getSite()))
                                    e.setEnd(intersection);
                            }
                            
                            Vector2 start = e.getStart();
                            Vector2 end = e.getEnd();
                            
                            Vector2 sourceToStart = Vector2.sub(perLine.getStart(), perLine.getA());
                            Vector2 sourceToInter = Vector2.sub(intersection, perLine.getA());
                            
                            if (compareDirections(sourceToStart, sourceToInter))
                            {
                                startSide.add(intersection);
                            }
                            else
                            {
                                endSide.add(intersection);
                            }
                        }
                    }
                }
                // If both of the cross-products are on the same side
                else if (spatialSite >= 0 && spatialMid >= 0)
                {
                    // If the midpoint vector is between the edge vector and
                    // the site vector
                    if (spatialSite >= spatialMid)
                    {
                        Vector2 intersection = perLine.intersect(e);
                        
                        if (intersection != null)
                        {
                            boolean isStart = true;
                            Vector2 start = e.getStart();
                            Vector2 end = e.getEnd();
                            
                            if (intersection.dist(start) > intersection.dist(end))
                                isStart = false;
                            
                            if (isStart)
                            {
                                if (intersection.dist(cell.getSite()) < start.dist(cell.getSite()))
                                    e.setStart(intersection);
                            }
                            else
                            {
                                if (intersection.dist(cell.getSite()) < end.dist(cell.getSite()))
                                    e.setEnd(intersection);
                            }
                            
                            
                            Vector2 sourceToStart = Vector2.sub(e.getStart(), e.getA());
                            Vector2 sourceToInter = Vector2.sub(intersection, e.getA());
                            
                            if (compareDirections(sourceToStart, sourceToInter))
                            {
                                startSide.add(intersection);
                            }
                            else
                            {
                                endSide.add(intersection);
                            }
                        }
                    }
                }
            }
            
            
            //cells.add(c);
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
