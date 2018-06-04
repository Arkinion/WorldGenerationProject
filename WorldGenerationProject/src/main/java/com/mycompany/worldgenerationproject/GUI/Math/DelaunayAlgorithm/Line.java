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
    
    
    
    
    
    
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Line)
        {
            Line l = (Line)o;
            
            if (l.getA().equals(l.A)
                    && l.getSlope().equals(l.slope)
                    && l.getStart().equals(l.start)
                    && l.getEnd().equals(l.end))
                return true;
        }
        return false;
    }
    
    public Vector2 asVector()
    {
        return Vector2.sub(end, start);
    }
    
    public Vector2 intersect(Line l)
    {
        //System.out.println(l.slope);
        if (slope.getX() != 0 && l.slope.getX() != 0)
        {
            Vector2 B = l.getA();

            double m1 = slope.getY()/slope.getX();
            double m2 = l.slope.getY()/l.slope.getX();
            
            if (m1 == m2)
                return null;

            double x = ((m1*A.getX()) - (m2*B.getX()) - A.getY() + B.getY()) / (m1 - m2);

            double y = Vector2.add( A , Vector2.mult(slope,(x-A.getX())/slope.getX()) ).getY();

            return new Vector2(x, y);
        }
        else if (slope.getX() == 0 && l.slope.getX() == 0)
        {}
        else
        {
            if (slope.getX() == 0)
            {
                double xt = (A.getX() - l.A.getX()) / l.slope.getX();
                return Vector2.add( l.A, Vector2.mult(l.slope, xt) );
            }
            else
            {
                double xt = (l.A.getX() - A.getX()) / slope.getX();
                return Vector2.add( A, Vector2.mult(slope, xt) );
            }
        }
        return null;
    }
    
    public void bound(Rect box)
    {

        if (slope.getX() == 0)
        {
            start = new Vector2(A.getX(), box.getyMin());
            end = new Vector2(A.getX(), box.getyMax());
        }
        else if (slope.getY() == 0)
        {
            start = new Vector2(box.getxMin(), A.getY());
            end = new Vector2(box.getxMax(), A.getY());
        }
        // wtf is this. it doesn't work or make sense
        else
        {
            double mx = slope.getY() / slope.getX();
            
            double xt1 = (box.getxMin() - A.getX()) / slope.getX();
            double yt1 = (box.getyMin() - A.getY()) / slope.getY();
            double xt2 = (box.getxMax() - A.getX()) / slope.getX();
            double yt2 = (box.getyMax() - A.getY()) / slope.getY();
            
            Vector2 xMin = Vector2.add(A, Vector2.mult(slope, xt1));
            Vector2 yMin = Vector2.add(A, Vector2.mult(slope, yt1));
            Vector2 xMax = Vector2.add(A, Vector2.mult(slope, xt2));
            Vector2 yMax = Vector2.add(A, Vector2.mult(slope, yt2));
            
            if (mx > 0)
            {
                if (start == null && xMin.getY() >= box.getyMin())
                    start = xMin;
                else
                    start = yMin;
                
                if (end == null && xMax.getY() <= box.getyMax())
                    end = xMax;
                else
                    end = yMax;
            }
            else
            {
                if (start == null && xMin.getY() <= box.getyMax())
                    start = xMin;
                else
                    start = yMax;
                
                if (end == null && xMax.getY() >= box.getyMin())
                    end = xMax;
                else
                    end = yMin;
            }
        }
    }
    
    public boolean onLine(Vector2 p)
    {
        // (y2 - y1) = mx(x2 - x1)
        
        if (slope.getX() != 0)
        {
            double mx = slope.getY() / slope.getX();
            
            /*if (mx == 0)
            {
                return A.getY() == p.getY();
            }*/
            
            double y = p.getY() - A.getY();
            double x = mx * (p.getX()-A.getX());
            
            if (x == y)
                return true;
        }
        else
        {
            if (p.getX() == A.getX())
                return true;
        }
        return false;
    }
    
    @Override
    public String toString()
    {
        return start + " : " + end;
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
