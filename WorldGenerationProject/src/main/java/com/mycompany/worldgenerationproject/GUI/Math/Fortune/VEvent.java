/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worldgenerationproject.GUI.Math.Fortune;

import com.mycompany.worldgenerationproject.GUI.Math.Vector2;

/**
 *
 * @author User
 */
class VEvent implements Comparable
{
    
    private Vector2 point;
    private boolean pe;
    private double y;
    private VParabola arch;
    
    
    
    
    
    
    public VEvent(Vector2 point_, boolean pe_)
    {
        point = point_;
        pe = pe_;
        y = point.getY();
        arch = null;
    }
    
    
    
    
    
    
    /*
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof VEvent)
        {
            VEvent e = (VEvent)o;
            
            if (point.equals(e.getPoint()) && pe == e.isPe())
                return true;
        }
        return false;
    }
    */
    
    public Vector2 getPoint()
    {
        return point;
    }
    
    public void setPoint(Vector2 point)
    {
        this.point = point;
    }
    
    public boolean isPe()
    {
        return pe;
    }
    
    public void setPe(boolean pe)
    {
        this.pe = pe;
    }
    
    public double getY()
    {
        return y;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
    
    public VParabola getArch()
    {
        return arch;
    }
    
    public void setArch(VParabola arch)
    {
        this.arch = arch;
    }
    
    
    
    
    
    
    @Override
    public int compareTo(Object o)
    {
        if (o instanceof VEvent)
        {
            VEvent e = (VEvent)o;
            
            
            if (getY() < e.getY())
                return 1;
            else if (getY() > e.getY())
                return -1;
        }
        return 0;
    }
    
}
