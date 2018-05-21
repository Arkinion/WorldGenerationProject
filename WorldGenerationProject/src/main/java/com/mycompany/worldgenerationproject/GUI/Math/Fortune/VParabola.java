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
public class VParabola
{
    
    private boolean leaf;
    private Vector2 site;
    private VEdge edge;
    private VEvent cEvent;
    private VParabola parent;
    private VParabola left;
    private VParabola right;
    
    
    
    
    
    
    public VParabola()
    {
        leaf = false;
        site = null;
        edge = null;
        cEvent = null;
        parent = null;
    }
    
    public VParabola(Vector2 site_)
    {
        leaf = true;
        site = site_;
        cEvent = null;
        edge = null;
        parent = null;
    }
    
    
    
    
    
    
    public boolean isLeaf()
    {
        return leaf;
    }
    
    public void setLeaf(boolean leaf)
    {
        this.leaf = leaf;
    }
    
    public Vector2 getSite()
    {
        return site;
    }
    
    public void setSite(Vector2 site)
    {
        this.site = site;
    }
    
    public VEdge getEdge()
    {
        return edge;
    }
    
    public void setEdge(VEdge edge)
    {
        this.edge = edge;
    }
    
    public VEvent getcEvent()
    {
        return cEvent;
    }
    
    public void setcEvent(VEvent cEvent)
    {
        this.cEvent = cEvent;
    }
    
    public VParabola getParent()
    {
        return parent;
    }
    
    public void setParent(VParabola parent)
    {
        this.parent = parent;
    }
    
    public VParabola getLeft() {
        return left;
    }
    
    public void setLeft(VParabola left)
    {
        this.left = left;
        left.setParent(this);
    }
    
    public VParabola getRight()
    {
        return right;
    }
    
    public void setRight(VParabola right)
    {
        this.right = right;
        right.setParent(this);
    }
    
    
    
    
    
    
    public static VParabola getLeft(VParabola p)
    {
        return getLeftChild(getLeftParent(p));
    }
    
    public static VParabola getRight(VParabola p)
    {
        return getRightChild(getRightParent(p));
    }
    
    public static VParabola getLeftParent(VParabola p)
    {
        VParabola par = p.getParent();
            if (par == null)
                return null;
        VParabola pLast = p;
        
        while (par.getLeft() == pLast)
        {
            if (par.getParent() == null)
                return null;
            pLast = par;
            par = par.getParent();
        }
        
        return par;
    }
    
    public static VParabola getRightParent(VParabola p)
    {
        VParabola par = p.getParent();
        if (par == null)
            return null;
        VParabola pLast = p;
        
        while (par.getRight() == pLast)
        {
            if (par.getParent() == null)
                return null;
            pLast = par;
            par = par.getParent();
        }
        
        return par;
    }
    
    public static VParabola getLeftChild(VParabola p)
    {
        if (p == null)
            return null;
        
        VParabola par = p.getLeft();
        
        while (!par.isLeaf())
        {
            par = par.getRight();
        }
        
        return par;
    }
    
    public static VParabola getRightChild(VParabola p)
    {
        if (p == null)
            return null;
        
        VParabola par = p.getRight();
        
        while (!par.isLeaf())
        {
            par = par.getLeft();
        }
        
        return par;
    }
    
}