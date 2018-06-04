/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worldgenerationproject.GUI.Math.DelaunayAlgorithm;

import com.mycompany.worldgenerationproject.GUI.Math.Vector2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author rose_880118
 */
public class Cell
{
    
    private Vector2 site;
    private List<Line> edges;
    private List<Vector2> circs;
    
    
    
    
    
    
    public Cell()
    {
        site = new Vector2();
        edges = new ArrayList<Line>();
        circs = new ArrayList<Vector2>();
    }
    
    public Cell(Vector2 site_)
    {
        site = site_;
        edges = new ArrayList<Line>();
        circs = new ArrayList<Vector2>();
    }
    
    public Cell(Vector2 site_, List<Line> edges_)
    {
        site = site_;
        edges = edges_;
        circs = new ArrayList<Vector2>();
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
    
    public void addCirc(Vector2 circ)
    {
        if (!circs.contains(circ))
            circs.add(circ);
    }
    
    public List<Vector2> getCircs()
    {
        return circs;
    }
    
    
    
    
    
    
    public boolean contains(Vector2 p)
    {
        for (Line edge : edges)
        {
            Vector2 v1 = Vector2.sub(site, edge.getStart());
            Vector2 v2 = Vector2.sub(edge.getEnd(), edge.getStart());
            
            if (v1.cross(v2) < 0)
            {
                v1 = Vector2.sub(p, edge.getStart());
                
                if (v1.cross(v2) > 0)
                    return false;
            }
            else
            {
                v1 = Vector2.sub(p, edge.getStart());
                
                if (v1.cross(v2) < 0)
                {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public void addEdge(Line l)
    {
        edges.add(l);
    }
    
    public void cleanCircs(Vector2 s)
    {
        for (int i = circs.size() - 1; i >= 0; i--)
        {
            if (s.dist(s) < circs.get(i).dist(site))
                circs.remove(i);
        }
    }
    
}
