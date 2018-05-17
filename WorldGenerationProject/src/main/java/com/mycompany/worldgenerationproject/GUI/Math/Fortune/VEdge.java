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
public class VEdge
{
    
    private Vector2 start;
    private Vector2 end;
    private Vector2 direction;
    private Vector2 left;
    private Vector2 right;
    
    private double f;
    private double g;
    
    private VEdge neighbor;
    
    
    
    
    
    
    public VEdge(Vector2 start_, Vector2 left_, Vector2 right_)
    {
        start = start_;
        left = left_;
        right = right_;
        direction = null;
        end = null;
        
        f = (right.getX() - left.getX()) / (left.getY() - right.getY());
        g = start.getY() - f * start.getX();
        direction = new Vector2(right.getY() - left.getY(), -(right.getX() - left.getX()));
    }
    
    
    
    
    
    
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof VEdge)
        {
            VEdge e = (VEdge)o;
            
            if (start.equals(e.getStart()) && end.equals(e.getEnd()))
                return true;
        }
        return false;
    }
    
    public Vector2 getStart()
    {
        return start;
    }
    
    public void setStart(Vector2 start)
    {
        this.start = start;
    }
    
    public Vector2 getEnd()
    {
        return end;
    }
    
    public void setEnd(Vector2 end)
    {
        this.end = end;
    }
    
    public Vector2 getDirection()
    {
        return direction;
    }
    
    public void setDirection(Vector2 direction)
    {
        this.direction = direction;
    }
    
    public Vector2 getLeft()
    {
        return left;
    }
    
    public void setLeft(Vector2 left)
    {
        this.left = left;
    }
    
    public Vector2 getRight()
    {
        return right;
    }
    
    public void setRight(Vector2 right)
    {
        this.right = right;
    }
    
    public double getF()
    {
        return f;
    }
    
    public void setF(double f)
    {
        this.f = f;
    }
    
    public double getG()
    {
        return g;
    }
    
    public void setG(double g)
    {
        this.g = g;
    }
    
    public VEdge getNeighbor()
    {
        return neighbor;
    }
    
    public void setNeighbor(VEdge neighbor)
    {
        this.neighbor = neighbor;
    }
    
}
