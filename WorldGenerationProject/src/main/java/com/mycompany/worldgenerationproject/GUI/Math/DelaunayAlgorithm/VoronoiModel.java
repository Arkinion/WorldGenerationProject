/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worldgenerationproject.GUI.Math.DelaunayAlgorithm;

import com.mycompany.worldgenerationproject.GUI.Math.Vector2;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class VoronoiModel
{
    
    private Vector2 dimensions;
    private List<Vector2> sites;
    
    
    
    
    
    
    public VoronoiModel()
    {
        dimensions = new Vector2();
        sites = new ArrayList<Vector2>();
    }
    
    
    
    
    
    
    public Vector2 getDimensions()
    {
        return dimensions;
    }
    
    public void setDimensions(Vector2 dimensions)
    {
        this.dimensions = dimensions;
    }
    
    public List<Vector2> getSites()
    {
        return sites;
    }
    
    public void setSites(List<Vector2> sites)
    {
        this.sites = sites;
    }
    
    
    
    
    
    
    public void generateSites(int s)
    {
        sites = new ArrayList<Vector2>();
        
        for (int i = 0; i < s; i++)
        {
            int x;
            int y;
            boolean contains;
            do
            {
                contains = false;
                
                x = (int)(Math.random() * (dimensions.getX()));
                y = (int)(Math.random() * (dimensions.getY()));
                
                for (Vector2 v : sites)
                {
                    if (x == v.getX() && y == v.getY())
                        contains = true;
                }
            } while (contains);
            
            sites.add(new Vector2(x, y));
        }
    }
    
}
