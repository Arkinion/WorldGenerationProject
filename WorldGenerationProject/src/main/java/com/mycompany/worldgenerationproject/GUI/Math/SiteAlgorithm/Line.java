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
    
    
    
    
    public Line()
    {
        A = new Vector2();
        B = new Vector2();
        slope = new Vector2();
    }
    
    public Line(Vector2 A_, Vector2 slope_)
    {
        A = A_;
        B = new Vector2();
        slope = slope_;
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
    
    
    
    
    
    
    public Vector2 asVector()
    {
        return Vector2.sub(end, start);
    }
    
    public Vector2 intersect(Line l)
    {
        Vector2 output;

        double a1 = B.getY() - A.getY();
        double b1 = A.getX() - B.getX();
        double c1 = a1 * A.getX() + b1 * A.getY();

        double a2 = l.getB().getY() - l.getA().getY();
        double b2 = l.getA().getX() - l.getB().getX();
        double c2 = a2 * l.getA().getX() + b2 * l.getA().getY();


        double det = a1 * b2 - a2 * b1;

        if (det == 0)
        {
            // They are parallel.
            return null;
        }
        else
        {
            double x = (b2 * c1 - b1 * c2) / det;
            double y = (a1 * c1 - a2 * c1) / det;
            output = new Vector2(x, y);
        }

        if ((Math.min(A.getX(), B.getX()) <= output.getX()
            && output.getX() <= Math.max(A.getX(), B.getX())
            && Math.min(A.getY(), B.getY()) <= output.getY()
            && output.getY() <= Math.max(A.getY(), B.getY())) &&
            (Math.min(l.getA().getX(), l.getB().getX()) <= output.getX()
            && output.getX() <= Math.max(l.getA().getX(), l.getB().getX())
            && Math.min(l.getA().getY(), l.getB().getY()) <= output.getY()
            && output.getY() <= Math.max(l.getA().getY(), l.getB().getY())))
        {
            return null;
        }
        return output;
    }
    
    public void bound(Rect box, Vector2 slope)
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
            
            x1 = Vector2.add(A, Vector2.mult(slope, xt1)).getX();
            y1 = Vector2.add(A, Vector2.mult(slope, yt1)).getY();
            x2 = Vector2.add(A, Vector2.mult(slope, xt2)).getX();
            y2 = Vector2.add(A, Vector2.mult(slope, yt2)).getY();
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
