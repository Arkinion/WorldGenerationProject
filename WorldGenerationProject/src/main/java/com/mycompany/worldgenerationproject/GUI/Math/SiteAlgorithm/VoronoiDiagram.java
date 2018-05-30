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
    
    
    
    
    
    
    public void add(Cell c)
    {
        for (Cell cell : cells)
        {

            Vector2 between = Vector2.sub(cell.getSite(), c.getSite());
            Vector2 mid = Vector2.add(cell.getSite(), between.div(2));

            for (Line e : cell.getEdges())
            {
                // MUST FIX
                ArrayList<Vector2> startSide = new ArrayList<Vector2>();
                ArrayList<Vector2> endSide = new ArrayList<Vector2>();
                
                Vector2 siteVec = Vector2.add(e.getA(), cell.getSite()).normalize();
                Vector2 midVec = Vector2.add(e.getA(), mid).normalize();
                Vector2 edgeVec = Vector2.add(e.getA(), e.asVector()).normalize();

                double spatialSite = edgeVec.cross(siteVec);
                double spatialMid = edgeVec.cross(midVec);
                
                Line perLine = Line.perLine( new Line(mid, between.normalize()) );
                perLine.bound(bounds);
                
                if (spatialSite < 0 && spatialMid < 0)
                {
                    if (spatialSite <= spatialMid)
                    {
                        Vector2 intersection = perLine.intersect(e);
                        
                        if (intersection != null)
                        {
                            boolean isStart = true;
                            Vector2 start = e.getStart();
                            Vector2 end = e.getEnd();
                            
                            if (isStart)
                            {
                                if (intersection.dist(cell.getSite()) < start.dist(cell.getSite()))
                                {
                                    e.setStart(intersection);
                                }
                            }
                            else
                            {
                                if (intersection.dist(cell.getSite()) < end.dist(cell.getSite()))
                                {
                                    e.setEnd(intersection);
                                }
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
                else if (spatialSite >= 0 && spatialMid >= 0)
                {
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
