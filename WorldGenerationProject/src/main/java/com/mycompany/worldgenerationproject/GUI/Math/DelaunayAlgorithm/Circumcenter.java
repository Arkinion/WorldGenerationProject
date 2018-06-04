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
 * @author nicho
 */
public class Circumcenter
{
    private Vector2 center;
    private List<Cell> parts;
    
    public Circumcenter(Vector2 c)
    {
        center = c;
        parts = new ArrayList<Cell>();
    }

    public Vector2 getCenter()
    {
        return center;
    }

    public void setCenter(Vector2 center)
    {
        this.center = center;
    }

    public List<Cell> getParts()
    {
        return parts;
    }
    
    public void addPart(Cell c)
    {
        if (!parts.contains(c))
            parts.add(c);
    }
    
    public boolean equals(Object o)
    {
        if (o instanceof Circumcenter)
        {
            Circumcenter e = (Circumcenter)o;
            if (e.center.equals(center)/* && parts.equals(e.parts)*/)
                return true;
        }
        return false;
    }
    
    
}
