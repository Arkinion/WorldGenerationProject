/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worldgenerationproject.GUI.Math;

/**
 *
 * @author User
 */
public class Rect
{
    
    private float xMin;
    private float xMax;
    private float yMin;
    private float yMax;
    
    
    
    
    
    
    public Rect()
    {
        xMin = 0;
        xMax = 0;
        yMin = 0;
        yMax = 0;
    }
    
    public Rect(float xMin_, float xMax_, float yMin_, float yMax_)
    {
        xMin = xMin_;
        xMax = xMax_;
        yMin = yMin_;
        yMax = yMax_;
    }
    
    
    
    
    
    
    public float getxMin()
    {
        return xMin;
    }
    
    public void setxMin(float xMin)
    {
        this.xMin = xMin;
    }
    
    public float getxMax()
    {
        return xMax;
    }
    
    public void setxMax(float xMax)
    {
        this.xMax = xMax;
    }
    
    public float getyMin()
    {
        return yMin;
    }
    
    public void setyMin(float yMin)
    {
        this.yMin = yMin;
    }
    
    public float getyMax()
    {
        return yMax;
    }
    
    public void setyMax(float yMax)
    {
        this.yMax = yMax;
    }
    
}
