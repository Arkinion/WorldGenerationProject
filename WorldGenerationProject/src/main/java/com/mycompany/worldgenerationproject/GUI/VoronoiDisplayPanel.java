/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.worldgenerationproject.GUI;

import com.mycompany.worldgenerationproject.GUI.Math.DelaunayAlgorithm.Cell;
import com.mycompany.worldgenerationproject.GUI.Math.DelaunayAlgorithm.Line;
import com.mycompany.worldgenerationproject.GUI.Math.DelaunayAlgorithm.VoronoiDiagram;
import com.mycompany.worldgenerationproject.GUI.Math.DelaunayAlgorithm.VoronoiModel;
import com.mycompany.worldgenerationproject.GUI.Math.Vector2;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author rose_880118
 */
public class VoronoiDisplayPanel extends javax.swing.JPanel
{
    
    VoronoiDiagram diagram;

    /**
     * Creates new form VoronoiDisplayPanel
     */
    public VoronoiDisplayPanel()
    {
        initComponents();
        
        VoronoiModel model = new VoronoiModel();
        model.setDimensions( new Vector2(775, 565) );
        model.generateSites(4);
        
        HashSet<Vector2> sites = new HashSet<Vector2>();
        sites.add(new Vector2(200, 200));
        sites.add(new Vector2(600, 200));
        sites.add(new Vector2(400, 100));
        sites.add(new Vector2(400, 300));
        model.setSites(sites);
        
        diagram = new VoronoiDiagram(model);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    @Override
    public void paintComponent(Graphics g)
    {
        
        super.paintComponent(g);
        
        for (Cell c : diagram.getCells())
        {
            for (Line l : c.getEdges())
            {
                g.drawLine((int)l.getStart().getX(), (int)l.getStart().getY(), (int)l.getEnd().getX(), (int)l.getEnd().getY());
                
                //System.out.println(l);
            }
            
            Vector2 site = c.getSite();
            g.fillOval((int)site.getX(), (int)site.getY(), 2, 2);
        }
        
    }
    
}
