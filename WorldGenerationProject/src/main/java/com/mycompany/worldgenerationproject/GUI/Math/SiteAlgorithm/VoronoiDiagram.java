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
                boolean startSet = false;
                boolean endSet = false;
                
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
                            boolean isPerStart = true;
                            Vector2 start = e.getStart();
                            Vector2 end = e.getEnd();
                            
                            if (intersection.dist(start) > intersection.dist(end))
                                isStart = false;
                            if (intersection.dist(perLine.getStart()) > intersection.dist(perLine.getEnd()))
                                isPerStart = false;
                            
                            if (startSet)
                            {
                                isPerStart = false;
                            }
                            if (endSet)
                            {
                                isPerStart = true;
                            }
                            
                            // Deal with edge case where intersection is closer to start than end after start was changed
                            if (isStart)
                            {
                                if (intersection.dist(cell.getSite()) < start.dist(cell.getSite()))
                                {
                                    e.setStart(intersection);
                                    if (isPerStart)
                                    {
                                        e.setStart(intersection);
                                        perLine.setStart(intersection);
                                    }
                                    else
                                    {
                                        e.setStart(intersection);
                                        perLine.setEnd(intersection);
                                    }
                                }
                            }
                            else
                            {
                                if (intersection.dist(cell.getSite()) < end.dist(cell.getSite()))
                                {
                                    if (isPerStart)
                                        if (intersection.dist(cell.getSite()) < intersection.dist(perLine.getStart()))
                                        {
                                            e.setEnd(intersection);
                                            perLine.setStart(intersection);
                                        }
                                    else
                                        if (intersection.dist(cell.getSite()) < intersection.dist(perLine.getEnd()))
                                        {
                                            e.setEnd(intersection);
                                            perLine.setEnd(intersection);
                                        }
                                }
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
