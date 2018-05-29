/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worldgenerationproject.GUI.Math.SiteAlgorithm;

import com.mycompany.worldgenerationproject.GUI.Math.Vector2;

/**
 *
 * @author User
 */
public class Rect
{
    
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    
    
    
    
    
    
    public Rect()
    {
        xMin = 0;
        xMax = 0;
        yMin = 0;
        yMax = 0;
    }
    
    public Rect(double xMin_, double xMax_, double yMin_, double yMax_)
    {
        xMin = xMin_;
        xMax = xMax_;
        yMin = yMin_;
        yMax = yMax_;
    }
    
    
    
    
    
    
    public double getxMin()
    {
        return xMin;
    }
    
    public void setxMin(double xMin)
    {
        this.xMin = xMin;
    }
    
    public double getxMax()
    {
        return xMax;
    }
    
    public void setxMax(double xMax)
    {
        this.xMax = xMax;
    }
    
    public double getyMin()
    {
        return yMin;
    }
    
    public void setyMin(double yMin)
    {
        this.yMin = yMin;
    }
    
    public double getyMax()
    {
        return yMax;
    }
    
    public void setyMax(double yMax)
    {
        this.yMax = yMax;
    }
    
    
    
    
    
    
    public boolean contains(Vector2 p)
    {
        if (p.getX() > xMin
                && p.getX() < xMax
                && p.getY() > yMin
                && p.getY() < yMax)
            return true;
        return false;
    }
    
}
