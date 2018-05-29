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
public class Line
{
    
    private Vector2 A;
    
    private Vector2 start;
    private Vector2 end;
    
    private Vector2 slope;
    
    
    
    
    
    
    public Line(Vector2 A_, Vector2 slope_)
    {
        A = A_;
        slope = slope_;
        
        start = null;
        end = null;
    }
    
    
    
    
    
    
    public Vector2 getA()
    {
        return A;
    }
    
    public void setA(Vector2 A)
    {
        this.A = A;
    }
    
    public Vector2 getSlope()
    {
        return slope;
    }
    
    public void setSlope(Vector2 slope)
    {
        this.slope = slope;
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
    
    
    
    
    
    
    public Vector2 asVector()
    {
        return Vector2.sub(end, start);
    }
    
    public Vector2 intersect(Line l)
    {
        Vector2 output;
        Vector2 B = l.getA();

        double m1 = slope.getY()/slope.getX();
        double m2 = l.slope.getY()/l.slope.getX();
        
        double x = ((m1*A.getY()) - (m2*B.getY()) - A.getY() + B.getY()) / (m1 - m2);
        
        double y = Vector2.add( A , Vector2.mult(slope,x-A.getX()) ).getY();
        
        return new Vector2(x, y);
    }
    
    public void bound(Rect box)
    {
        double x1;
        double y1;
        double x2;
        double y2;

        if (slope.getX() == 0)
        {
            x1 = A.getX();
            y1 = box.getyMin();
            x2 = A.getX();
            y2 = box.getyMax();
        }
        else
        {
            double xt1 = (box.getxMin() - A.getX()) / slope.getX();
            double yt1 = (box.getyMin() - A.getY()) / slope.getY();
            double xt2 = (box.getxMax() - A.getX()) / slope.getX();
            double yt2 = (box.getyMax() - A.getY()) / slope.getY();
            
            x1 = Vector2.add(A, Vector2.mult(slope.div(slope.getX()), xt1)).getX();
            y1 = Vector2.add(A, Vector2.mult(slope.div(slope.getY()), yt1)).getY();
            x2 = Vector2.add(A, Vector2.mult(slope.div(slope.getX()), xt2)).getX();
            y2 = Vector2.add(A, Vector2.mult(slope.div(slope.getY()), yt2)).getY();
        }

        start = new Vector2(x1, y1);
        end = new Vector2(x2, y2);
    }
    
    
    
    
    
    
    // Returns a line that is a copy of the given Line with a perpendicular slope.
    public static Line perLine(Line l)
    {
        Line output;
        
        Vector2 slope = l.slope;
        
        double x = slope.getX();
        double y;
        
        y = -x;
        x = slope.getY();
        
        slope = new Vector2(x, y);
        
        output = new Line(l.A, slope);
        
        return output;
    }
    
}
