/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worldgenerationproject.GUI.Math;

/**
 *
 * @author rose_880118
 */
/*
 * Represents a vector in a 2d plane.
*/
public class Vector2 implements Comparable<Vector2>
{
    
    /*
     * Instance variables.
    */
    private double x;
    private double y;
    
    
    
    
    
    
    /*
     * Constructs a Vector2 with default values.
    */
    public Vector2()
    {
        x = 0;
        y = 0;
    }
    
    /*
     * Constructs a Vector2 with the given x and y values.
    */
    public Vector2(double x_, double y_)
    {
        x = x_;
        y = y_;
    }
    
    
    
    
    
    
    /*
     * Gets the x of this vector.
    */
    public double getX()
    {
        return x;
    }
    
    /*
     * Sets the x of this vector.
    */
    public void setX(double x)
    {
        this.x = x;
    }
    
    /*
     * Gets the y of this vector.
    */
    public double getY()
    {
        return y;
    }
    
    /*
     * Sets the y of this vector.
    */
    public void setY(double y)
    {
        this.y = y;
    }
    
    
    
    
    
    
    /*
     * Returns a copy of this vector.
    */
    @Override
    public Vector2 clone()
    {
        return new Vector2(x, y);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Vector2)
        {
            Vector2 v = (Vector2)o;
            if (x == v.getX() && y == v.getY())
                return true;
        }
        return false;
    }
    
    @Override
    public int compareTo (Vector2 other)
    {
            if (getY() == other.getY())
            {
                if (getX() == other.getX()) return 0;
                else if (getX() > other.getX()) return 1;
                else return -1;
            }
            else if (getY() > other.getY())
            {
                return 1;
            }
            else
            {
		return -1;
            }
	}
    
    /*
     * Returns the magnitude of this vector.
    */
    public double mag()
    {
        return Math.sqrt(x * x + y * y);
    }
    
    /*
     * Returns the square magnitude of this vector (saves on computational complexity).
    */
    public double magSq()
    {
        return x * x + y * y;
    }
    
    /*
     * Scales this vector out or in to a given magnitude.
    */
    public Vector2 setMag(double newMag)
    {
        double mag = mag();
        
        if (mag != 1)
        {
            normalize();
        }
        
        mult(newMag);
        
        return this;
    }
    
    /*
     * Limits the magnitude of this vector to the max value given.
    */
    public Vector2 limit(double max)
    {
        if (mag() > max)
        {
            setMag(max);
        }
        
        return this;
    }
    
    /*
     * Normalizes this vector.
    */
    public Vector2 normalize()
    {
        double mag = mag();
        
        if (mag != 0)
            div(mag);
        
        return this;
    }
    
    /*
     * Adds a vector to this vector.
    */
    public Vector2 add(Vector2 v)
    {
        x += v.getX();
        y += v.getY();
        
        return this;
    }
    
    /*
     * Subtracts a vector from this vector.
    */
    public Vector2 sub(Vector2 v)
    {
        x -= v.getX();
        y -= v.getY();
        
        return this;
    }
    
    /*
     * Multiplies this vector by a number.
    */
    public Vector2 mult(double n)
    {
        x *= n;
        y *= n;
        
        return this;
    }
    
    /*
     * Divides this vector by a number.
    */
    public Vector2 div(double n)
    {
        if (n == 0)
            return null;
        
        x /= n;
        y /= n;
        
        return this;
    }
    
    /*
     * Linearly interpolates this vector to another vector by an amount.
    */
    public Vector2 lerp(Vector2 v, double amt)
    {
        mult(amt);
        add(v.clone().mult(1 - amt));
        
        return this;
    }
    
    /*
     * Rotates this vector by an angle given in radians.
    */
    public Vector2 rotate(double theta)
    {
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        
        double newX = x * cos - y * sin;
        double newY = x * sin + y * cos;
        
        x = newX;
        y = newY;
        
        return this;
    }
    
    /*
     * Returns the dot product of this vector and another.
    */
    public double dot(Vector2 v)
    {
        return x * v.getX() + y * v.getY();
    }
    
    /*
     * Returns the cross product of this vector and another.
    */
    public double cross(Vector2 v)
    {
        return x * v.getY() - y * v.getX();
    }
    
    /*
     * Returns the angle between this vector and another.
    */
    public double angleBetween(Vector2 v)
    {
        double dot = dot(v);
        double theta = Math.acos(dot / (mag() * v.mag()));
        
        return theta;
    }
    
    /*
     * Returns the distance between this vector and another (treating them as points).
    */
    public double dist(Vector2 v)
    {
        double inside = (v.getX() - x) + (v.getY() - y);
        return Math.sqrt(inside);
    }
    
    /*
     * Returns the heading (angle) of this vector in radians.
    */
    public double heading()
    {
        return Math.atan(y / x);
    }
    
    
    
    
    
    
    /*
     * Returns a vector plus another vector.
    */
    public static Vector2 add(Vector2 v1, Vector2 v2)
    {
        double newX = v1.getX() + v2.getX();
        double newY = v1.getY() + v2.getY();
        
        return new Vector2(newX, newY);
    }
    
    /*
     * Returns a vector minus another vector.
    */
    public static Vector2 sub(Vector2 v1, Vector2 v2)
    {
        double newX = v1.getX() - v2.getX();
        double newY = v1.getY() - v2.getY();
        
        return new Vector2(newX, newY);
    }
    
    /*
     * Returns a vector multiplied by a number.
    */
    public static Vector2 mult(Vector2 v1, double n)
    {
        double newX = v1.getX() * n;
        double newY = v1.getY() * n;
        
        return new Vector2(newX, newY);
    }
    
    /*
     * Returns a vector divided by a number.
    */
    public static Vector2 div(Vector2 v1, double n)
    {
        if (n == 0)
            return null;
        
        double newX = v1.getX() / n;
        double newY = v1.getY() / n;
        
        return new Vector2(newX, newY);
    }
    
    /*
     * Returns a new vector linearly interpolated by the given amount.
    */
    public static Vector2 lerp(Vector2 v1, Vector2 v2, double amt)
    {
        Vector2 comp1 = v1.clone().mult(amt);
        Vector2 comp2 = (v2.clone().mult(1 - amt));
        
        return comp1.add(comp2);
    }
    
    /*
     * Returns a new unit vector rotated by a number of radians.
    */
    public static Vector2 rotate(Vector2 v, double theta)
    {
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        
        double x = v.getX() * cos - v.getY() * sin;
        double y = v.getX() * sin + v.getY() * cos;
        
        return new Vector2(x, y);
    }
    
    /*
     * Returns the dot product of two vectors.
    */
    public static double dot(Vector2 v1, Vector2 v2)
    {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }
    
    /*
     * Returns the cross product of two vectors.
    */
    public static double cross(Vector2 v1, Vector2 v2)
    {
        return v1.getX() * v2.getY() - v1.getY() * v2.getX();
    }
    
    /*
     * Returns the angle between two vectors in radians.
    */
    public static double angleBetween(Vector2 v1, Vector2 v2)
    {
        double dot = v1.dot(v2);
        double theta = Math.acos(dot / (v1.mag() * v2.mag()));
        
        return theta;
    }
    
    /*
     * Returns the distance between two vectors (treating them as points).
    */
    public static double dist(Vector2 v1, Vector2 v2)
    {
        double inside = (v2.getX() - v1.getX()) + (v2.getY() - v1.getY());
        return Math.sqrt(inside);
    }
    
    /*
     * Returns a new random unit vector.
    */
    public static Vector2 random()
    {
        double x = Math.random();
        double y = Math.random();
        
        Vector2 rand = new Vector2(x, y);
        rand.normalize();
        
        return rand;
    }
    
}
