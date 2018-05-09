/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worldgenerationproject.GUI.Math;

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
        if (!contains(c))
        {
            
            for (Cell cell : cells)
            {
                
                Vector2 between = Vector2.sub(cell.getSite(), c.getSite());
                Vector2 mid = Vector2.add(cell.getSite(), between);
                Vector2 slope = Vector2.rotate(between, (float)Math.PI).normalize();
                
                for (Line e : cell.getEdges())
                {
                    Vector2 siteVec = Vector2.add(e.getA(), cell.getSite());
                    Vector2 midVec = Vector2.add(e.getA(), mid);
                    Vector2 edgeVec = Vector2.add(e.getA(), e.asVector());
                    
                    float spatialSite = siteVec.dot(edgeVec);
                    float spatialMid = midVec.dot(edgeVec);
                    
                    
                    
                    
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
