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
public class Vector2
{
    
    /*
     * Instance variables.
    */
    private float x;
    private float y;
    
    
    
    
    
    
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
    public Vector2(float x_, float y_)
    {
        x = x_;
        y = y_;
    }
    
    
    
    
    
    
    /*
     * Gets the x of this vector.
    */
    public float getX()
    {
        return x;
    }
    
    /*
     * Sets the x of this vector.
    */
    public void setX(float x)
    {
        this.x = x;
    }
    
    /*
     * Gets the y of this vector.
    */
    public float getY()
    {
        return y;
    }
    
    /*
     * Sets the y of this vector.
    */
    public void setY(float y)
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
    
    /*
     * Returns the magnitude of this vector.
    */
    public float mag()
    {
        return (float)Math.sqrt(x * x + y * y);
    }
    
    /*
     * Returns the square magnitude of this vector (saves on computational complexity).
    */
    public float magSq()
    {
        return x * x + y * y;
    }
    
    /*
     * Scales this vector out or in to a given magnitude.
    */
    public Vector2 setMag(float newMag)
    {
        float mag = mag();
        
        if (mag != 1)
        {
            normalize();
        }
        
        mult(newMag);
        
        return this;
    }
    
    /*
     * Normalizes this vector.
    */
    public Vector2 normalize()
    {
        float mag = mag();
        
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
    public Vector2 mult(float n)
    {
        x *= n;
        y *= n;
        
        return this;
    }
    
    /*
     * Divides this vector by a number.
    */
    public Vector2 div(float n)
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
    public Vector2 lerp(Vector2 v, float amt)
    {
        mult(amt);
        add(v.clone().mult(1 - amt));
        
        return this;
    }
    
    /*
     * Rotates this vector by an angle given in radians.
    */
    public Vector2 rotate(float theta)
    {
        float cos = (float)Math.cos(theta);
        float sin = (float)Math.sin(theta);
        
        float newX = x * cos - y * sin;
        float newY = x * sin + y * cos;
        
        x = newX;
        y = newY;
        
        return this;
    }
    
    /*
     * Returns the dot product of this vector and another.
    */
    public float dot(Vector2 v)
    {
        return x * v.getX() + y * v.getY();
    }
    
    /*
     * Returns the cross product of this vector and another.
    */
    public float cross(Vector2 v)
    {
        return x * v.getY() - y * v.getX();
    }
    
    /*
     * Returns the angle between this vector and another.
    */
    public float angleBetween(Vector2 v)
    {
        float dot = dot(v);
        float theta = (float)Math.acos(dot / (mag() * v.mag()));
        
        return theta;
    }
    
    /*
     * Returns the distance between this vector and another (treating them as points).
    */
    public float dist(Vector2 v)
    {
        float inside = (v.getX() - x) + (v.getY() - y);
        return (float)Math.sqrt(inside);
    }
    
    /*
     * Returns the heading (angle) of this vector in radians.
    */
    public float heading()
    {
        return (float)Math.atan(y / x);
    }
    
    
    
    
    
    
    /*
     * Returns a vector plus another vector.
    */
    public static Vector2 add(Vector2 v1, Vector2 v2)
    {
        float newX = v1.getX() + v2.getX();
        float newY = v1.getY() + v2.getY();
        
        return new Vector2(newX, newY);
    }
    
    /*
     * Returns a vector minus another vector.
    */
    public static Vector2 sub(Vector2 v1, Vector2 v2)
    {
        float newX = v1.getX() - v2.getX();
        float newY = v1.getY() - v2.getY();
        
        return new Vector2(newX, newY);
    }
    
    /*
     * Returns a vector multiplied by a number.
    */
    public static Vector2 mult(Vector2 v1, float n)
    {
        float newX = v1.getX() * n;
        float newY = v1.getY() * n;
        
        return new Vector2(newX, newY);
    }
    
    /*
     * Returns a vector divided by a number.
    */
    public static Vector2 div(Vector2 v1, float n)
    {
        if (n == 0)
            return null;
        
        float newX = v1.getX() / n;
        float newY = v1.getY() / n;
        
        return new Vector2(newX, newY);
    }
    
    /*
     * Returns a new vector linearly interpolated by the given amount.
    */
    public static Vector2 lerp(Vector2 v1, Vector2 v2, float amt)
    {
        Vector2 comp1 = v1.clone().mult(amt);
        Vector2 comp2 = (v2.clone().mult(1 - amt));
        
        return comp1.add(comp2);
    }
    
    /*
     * Returns a new unit vector rotated by a number of radians.
    */
    public static Vector2 rotate(Vector2 v, float theta)
    {
        float cos = (float)Math.cos(theta);
        float sin = (float)Math.sin(theta);
        
        float x = v.getX() * cos - v.getY() * sin;
        float y = v.getX() * sin + v.getY() * cos;
        
        return new Vector2(x, y);
    }
    
    /*
     * Returns the dot product of two vectors.
    */
    public static float dot(Vector2 v1, Vector2 v2)
    {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }
    
    /*
     * Returns the cross product of two vectors.
    */
    public static float cross(Vector2 v1, Vector2 v2)
    {
        return v1.getX() * v2.getY() - v1.getY() * v2.getX();
    }
    
    /*
     * Returns the angle between two vectors in radians.
    */
    public static float angleBetween(Vector2 v1, Vector2 v2)
    {
        float dot = v1.dot(v2);
        float theta = (float)Math.acos(dot / (v1.mag() * v2.mag()));
        
        return theta;
    }
    
    /*
     * Returns the distance between two vectors (treating them as points).
    */
    public static float dist(Vector2 v1, Vector2 v2)
    {
        float inside = (v2.getX() - v1.getX()) + (v2.getY() - v1.getY());
        return (float)Math.sqrt(inside);
    }
    
    /*
     * Returns a new random unit vector.
    */
    public static Vector2 random()
    {
        float x = (float)Math.random();
        float y = (float)Math.random();
        
        Vector2 rand = new Vector2(x, y);
        rand.normalize();
        
        return rand;
    }
    
}
