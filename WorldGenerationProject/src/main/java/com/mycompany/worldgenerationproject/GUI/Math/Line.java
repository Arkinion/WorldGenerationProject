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
public class Line
{
    
    private Vector2 A;
    private Vector2 B;
    
    
    
    
    
    
    public Line()
    {
        A = new Vector2();
        B = new Vector2();
    }
    
    public Line(Vector2 A_)
    {
        A = A_;
        B = new Vector2();
    }
    
    public Line(Vector2 A_, Vector2 B_)
    {
        A = A_;
        B = B_;
    }
    
    
    
    
    
    
    public Vector2 getA()
    {
        return A;
    }
    
    public void setA(Vector2 A)
    {
        this.A = A;
    }
    
    public Vector2 getB()
    {
        return B;
    }
    
    public void setB(Vector2 B)
    {
        this.B = B;
    }
    
    
    
    
    
    
    public Vector2 slope()
    {
        return Vector2.sub(B, A).normalize();
    }
    
    public Vector2 asVector()
    {
        return Vector2.sub(B, A);
    }
    
    public Vector2 intersect(Line l)
    {
        Vector2 output;

        float a1 = B.getY() - A.getY();
        float b1 = A.getX() - B.getX();
        float c1 = a1 * A.getX() + b1 * A.getY();

        float a2 = l.getB().getY() - l.getA().getY();
        float b2 = l.getA().getX() - l.getB().getX();
        float c2 = a2 * l.getA().getX() + b2 * l.getA().getY();


        float det = a1 * b2 - a2 * b1;

        if (det == 0)
        {
            // They are parallel.
            return null;
        }
        else
        {
            float x = (b2 * c1 - b1 * c2) / det;
            float y = (a1 * c1 - a2 * c1) / det;
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
        float x1;
        float y1;
        float x2;
        float y2;

        if (slope.getX() == 0)
        {
            x1 = A.getX();
            y1 = box.getyMin();
            x2 = A.getX();
            y2 = box.getyMax();
        }
        else
        {
            float xt1 = (box.getxMin() - A.getX()) / slope.getX();
            float yt1 = (box.getyMin() - A.getY()) / slope.getY();
            float xt2 = (box.getxMax() - A.getX()) / slope.getX();
            float yt2 = (box.getyMax() - A.getY()) / slope.getY();
            
            x1 = Vector2.add(A, Vector2.mult(slope, xt1)).getX();
            y1 = Vector2.add(A, Vector2.mult(slope, yt1)).getY();
            x2 = Vector2.add(A, Vector2.mult(slope, xt2)).getX();
            y2 = Vector2.add(A, Vector2.mult(slope, yt2)).getY();
        }

        A = new Vector2(x1, y1);
        B = new Vector2(x2, y2);
    }
    
}
