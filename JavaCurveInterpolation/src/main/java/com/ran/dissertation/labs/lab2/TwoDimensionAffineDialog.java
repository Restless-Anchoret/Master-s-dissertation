package com.ran.dissertation.labs.lab2;

import com.ran.dissertation.ui.PresentationComboBox;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TwoDimensionAffineDialog extends JPanel {

    public TwoDimensionAffineDialog() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        moveOverXLabel = new javax.swing.JLabel();
        moveOverYLabel = new javax.swing.JLabel();
        moveXPlusButton = new javax.swing.JButton();
        moveXMinusButton = new javax.swing.JButton();
        moveYPlusButton = new javax.swing.JButton();
        moveYMinusButton = new javax.swing.JButton();
        firstSeparator = new javax.swing.JSeparator();
        chosenVerticeLabel = new javax.swing.JLabel();
        rotateLabel = new javax.swing.JLabel();
        rotatePlusButton = new javax.swing.JButton();
        rotateMinusButton = new javax.swing.JButton();
        secondSeparator = new javax.swing.JSeparator();
        reflectXButton = new javax.swing.JButton();
        reflectYButton = new javax.swing.JButton();
        reflectPivotButton = new javax.swing.JButton();
        thirdSeparator = new javax.swing.JSeparator();
        increaseButton = new javax.swing.JButton();
        decreaseButton = new javax.swing.JButton();
        chosenVerticeComboBox = new com.ran.dissertation.ui.PresentationComboBox<>();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Affine transformations"));

        moveOverXLabel.setText("Move over X axis:");

        moveOverYLabel.setText("Move over Y axis:");

        moveXPlusButton.setText("+");

        moveXMinusButton.setText("-");

        moveYPlusButton.setText("+");

        moveYMinusButton.setText("-");

        chosenVerticeLabel.setText("Chosen vertice:");

        rotateLabel.setText("Rotate:");

        rotatePlusButton.setText("+");

        rotateMinusButton.setText("-");

        reflectXButton.setText("Reflect over X axis");

        reflectYButton.setText("Reflect over Y axis");

        reflectPivotButton.setText("Reflect over pivot");

        increaseButton.setText("Increase");

        decreaseButton.setText("Decrease");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(secondSeparator)
                    .addComponent(reflectXButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(firstSeparator, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chosenVerticeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chosenVerticeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(reflectYButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reflectPivotButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(thirdSeparator)
                    .addComponent(increaseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(decreaseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(moveOverXLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(moveXPlusButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(moveOverYLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(moveYPlusButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(moveYMinusButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(moveXMinusButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(rotateLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                        .addComponent(rotatePlusButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rotateMinusButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moveXPlusButton)
                    .addComponent(moveOverXLabel)
                    .addComponent(moveXMinusButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moveYMinusButton)
                    .addComponent(moveYPlusButton)
                    .addComponent(moveOverYLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(firstSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chosenVerticeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chosenVerticeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotatePlusButton)
                    .addComponent(rotateMinusButton)
                    .addComponent(rotateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(secondSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reflectXButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reflectYButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reflectPivotButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(thirdSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(increaseButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(decreaseButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.ran.dissertation.ui.PresentationComboBox<Integer> chosenVerticeComboBox;
    private javax.swing.JLabel chosenVerticeLabel;
    private javax.swing.JButton decreaseButton;
    private javax.swing.JSeparator firstSeparator;
    private javax.swing.JButton increaseButton;
    private javax.swing.JLabel moveOverXLabel;
    private javax.swing.JLabel moveOverYLabel;
    private javax.swing.JButton moveXMinusButton;
    private javax.swing.JButton moveXPlusButton;
    private javax.swing.JButton moveYMinusButton;
    private javax.swing.JButton moveYPlusButton;
    private javax.swing.JButton reflectPivotButton;
    private javax.swing.JButton reflectXButton;
    private javax.swing.JButton reflectYButton;
    private javax.swing.JLabel rotateLabel;
    private javax.swing.JButton rotateMinusButton;
    private javax.swing.JButton rotatePlusButton;
    private javax.swing.JSeparator secondSeparator;
    private javax.swing.JSeparator thirdSeparator;
    // End of variables declaration//GEN-END:variables

    public PresentationComboBox<Integer> getChosenVerticeComboBox() {
        return chosenVerticeComboBox;
    }

    public JButton getDecreaseButton() {
        return decreaseButton;
    }

    public JButton getIncreaseButton() {
        return increaseButton;
    }

    public JButton getMoveXMinusButton() {
        return moveXMinusButton;
    }

    public JButton getMoveXPlusButton() {
        return moveXPlusButton;
    }

    public JButton getMoveYMinusButton() {
        return moveYMinusButton;
    }

    public JButton getMoveYPlusButton() {
        return moveYPlusButton;
    }

    public JButton getReflectPivotButton() {
        return reflectPivotButton;
    }

    public JButton getReflectXButton() {
        return reflectXButton;
    }

    public JButton getReflectYButton() {
        return reflectYButton;
    }

    public JButton getRotateMinusButton() {
        return rotateMinusButton;
    }

    public JButton getRotatePlusButton() {
        return rotatePlusButton;
    }

}
