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
public class Cell
{
    
    private Vector2 site;
    private List<Line> edges;
    
    
    
    
    
    
    public Cell()
    {
        site = new Vector2();
        edges = new ArrayList<Line>();
    }
    
    public Cell(Vector2 site_)
    {
        site = site_;
        edges = new ArrayList<Line>();
    }
    
    public Cell(Vector2 site_, List<Line> edges_)
    {
        site = site_;
        edges = edges_;
    }
    
    
    
    
    
    
    public Vector2 getSite()
    {
        return site;
    }
    
    public void setSite(Vector2 site)
    {
        this.site = site;
    }
    
    public List<Line> getEdges()
    {
        return edges;
    }
    
    public void setEdges(List<Line> edges)
    {
        this.edges = edges;
    }
    
    
    
    
    
    
    public boolean contains(Vector2 p)
    {
        for (Line edge : edges)
        {
            Vector2 v1 = Vector2.sub(site, edge.getA());
            Vector2 v2 = Vector2.sub(edge.getB(), edge.getA());
            
            if (v1.cross(v2) < 0)
            {
                v1 = Vector2.sub(p, edge.getA());
                
                if (v1.cross(v2) > 0)
                    return false;
            }
            else
            {
                v1 = Vector2.sub(p, edge.getA());
                
                if (v1.cross(v2) < 0)
                {
                    return false;
                }
            }
        }
        
        return true;
    }
    
}
