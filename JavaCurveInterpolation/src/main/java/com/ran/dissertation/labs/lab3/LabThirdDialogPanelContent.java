package com.ran.dissertation.labs.lab3;

import javax.swing.JPanel;

public class LabThirdDialogPanelContent extends JPanel {

    public LabThirdDialogPanelContent() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        threeDimensionAffineDialog = new com.ran.dissertation.labs.lab3.ThreeDimensionAffineDialog();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(threeDimensionAffineDialog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(threeDimensionAffineDialog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.ran.dissertation.labs.lab3.ThreeDimensionAffineDialog threeDimensionAffineDialog;
    // End of variables declaration//GEN-END:variables

    public ThreeDimensionAffineDialog getThreeDimensionAffineDialog() {
        return threeDimensionAffineDialog;
    }

}
