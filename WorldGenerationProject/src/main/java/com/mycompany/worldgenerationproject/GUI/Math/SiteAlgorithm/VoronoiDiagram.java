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
                    // If the midpoint vector is between the edge vector and
                    // the site vector
                    if (spatialSite >= spatialMid)
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
            }
            
            // Here the new edge will be added
            
            
            // NOTE: CONSIDER THE CASE WHERE THE NEW EDGE IS NOT INCLUDED
            // FIND PROPERTIES OF THIS
            // MAY BE COVERED
            
            
        }
        
        // Adding the new cell
        cells.add(c);
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
