package com.ran.dissertation.labs.lab3;

import com.ran.dissertation.ui.PresentationComboBox;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ThreeDimensionAffineDialog extends JPanel {

    public ThreeDimensionAffineDialog() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        moveOverXLabel = new javax.swing.JLabel();
        moveOverYLabel = new javax.swing.JLabel();
        moveOverZLabel = new javax.swing.JLabel();
        moveXPlusButton = new javax.swing.JButton();
        moveXMinusButton = new javax.swing.JButton();
        moveYPlusButton = new javax.swing.JButton();
        moveYMinusButton = new javax.swing.JButton();
        moveZPlusButton = new javax.swing.JButton();
        moveZMinusButton = new javax.swing.JButton();
        firstSeparator = new javax.swing.JSeparator();
        chosenVerticeLabel = new javax.swing.JLabel();
        rotateOverXLabel = new javax.swing.JLabel();
        rotateOverYLabel = new javax.swing.JLabel();
        rotateOverZLabel = new javax.swing.JLabel();
        rotateXPlusButton = new javax.swing.JButton();
        rotateXMinusButton = new javax.swing.JButton();
        rotateYPlusButton = new javax.swing.JButton();
        rotateYMinusButton = new javax.swing.JButton();
        rotateZPlusButton = new javax.swing.JButton();
        rotateZMinusButton = new javax.swing.JButton();
        secondSeparator = new javax.swing.JSeparator();
        reflectXButton = new javax.swing.JButton();
        reflectYButton = new javax.swing.JButton();
        reflectZButton = new javax.swing.JButton();
        reflectXoYButton = new javax.swing.JButton();
        reflectXoZButton = new javax.swing.JButton();
        reflectYoZButton = new javax.swing.JButton();
        reflectPivotButton = new javax.swing.JButton();
        thirdSeparator = new javax.swing.JSeparator();
        increaseButton = new javax.swing.JButton();
        decreaseButton = new javax.swing.JButton();
        chosenVerticeComboBox = new com.ran.dissertation.ui.PresentationComboBox<>();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Affine transformations"));

        moveOverXLabel.setText("Move over X axis:");

        moveOverYLabel.setText("Move over Y axis:");

        moveOverZLabel.setText("Move over Z axis:");

        moveXPlusButton.setText("+");

        moveXMinusButton.setText("-");

        moveYPlusButton.setText("+");

        moveYMinusButton.setText("-");

        moveZPlusButton.setText("+");

        moveZMinusButton.setText("-");

        chosenVerticeLabel.setText("Chosen vertice:");

        rotateOverXLabel.setText("Rotate over X axis:");

        rotateOverYLabel.setText("Rotate over Y axis:");

        rotateOverZLabel.setText("Rotate over Z axis:");

        rotateXPlusButton.setText("+");

        rotateXMinusButton.setText("-");

        rotateYPlusButton.setText("+");

        rotateYMinusButton.setText("-");

        rotateZPlusButton.setText("+");

        rotateZMinusButton.setText("-");

        reflectXButton.setText("Reflect over X axis");

        reflectYButton.setText("Reflect over Y axis");

        reflectZButton.setText("Reflect over Z axis");

        reflectXoYButton.setText("Reflect over XoY plane");

        reflectXoZButton.setText("Reflect over XoZ plane");

        reflectYoZButton.setText("Reflect over YoZ plane");

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
                    .addComponent(reflectZButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reflectXoYButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reflectXoZButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reflectYoZButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(moveOverYLabel)
                                    .addComponent(moveOverZLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(moveZPlusButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(moveYPlusButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(moveZMinusButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(moveYMinusButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(moveXMinusButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rotateOverXLabel)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(rotateOverZLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rotateOverYLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rotateXPlusButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rotateYPlusButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rotateZPlusButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rotateZMinusButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rotateYMinusButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rotateXMinusButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moveZPlusButton)
                    .addComponent(moveZMinusButton)
                    .addComponent(moveOverZLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(firstSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chosenVerticeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chosenVerticeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotateXPlusButton)
                    .addComponent(rotateXMinusButton)
                    .addComponent(rotateOverXLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotateYMinusButton)
                    .addComponent(rotateYPlusButton)
                    .addComponent(rotateOverYLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotateZPlusButton)
                    .addComponent(rotateZMinusButton)
                    .addComponent(rotateOverZLabel))
                .addGap(12, 12, 12)
                .addComponent(secondSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reflectXButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reflectYButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reflectZButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reflectXoYButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reflectXoZButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reflectYoZButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reflectPivotButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(thirdSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(increaseButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(decreaseButton))
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
    private javax.swing.JLabel moveOverZLabel;
    private javax.swing.JButton moveXMinusButton;
    private javax.swing.JButton moveXPlusButton;
    private javax.swing.JButton moveYMinusButton;
    private javax.swing.JButton moveYPlusButton;
    private javax.swing.JButton moveZMinusButton;
    private javax.swing.JButton moveZPlusButton;
    private javax.swing.JButton reflectPivotButton;
    private javax.swing.JButton reflectXButton;
    private javax.swing.JButton reflectXoYButton;
    private javax.swing.JButton reflectXoZButton;
    private javax.swing.JButton reflectYButton;
    private javax.swing.JButton reflectYoZButton;
    private javax.swing.JButton reflectZButton;
    private javax.swing.JLabel rotateOverXLabel;
    private javax.swing.JLabel rotateOverYLabel;
    private javax.swing.JLabel rotateOverZLabel;
    private javax.swing.JButton rotateXMinusButton;
    private javax.swing.JButton rotateXPlusButton;
    private javax.swing.JButton rotateYMinusButton;
    private javax.swing.JButton rotateYPlusButton;
    private javax.swing.JButton rotateZMinusButton;
    private javax.swing.JButton rotateZPlusButton;
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

    public JButton getMoveZMinusButton() {
        return moveZMinusButton;
    }

    public JButton getMoveZPlusButton() {
        return moveZPlusButton;
    }

    public JButton getReflectPivotButton() {
        return reflectPivotButton;
    }

    public JButton getReflectXButton() {
        return reflectXButton;
    }

    public JButton getReflectXoYButton() {
        return reflectXoYButton;
    }

    public JButton getReflectXoZButton() {
        return reflectXoZButton;
    }

    public JButton getReflectYButton() {
        return reflectYButton;
    }

    public JButton getReflectYoZButton() {
        return reflectYoZButton;
    }

    public JButton getReflectZButton() {
        return reflectZButton;
    }

    public JButton getRotateXMinusButton() {
        return rotateXMinusButton;
    }

    public JButton getRotateXPlusButton() {
        return rotateXPlusButton;
    }

    public JButton getRotateYMinusButton() {
        return rotateYMinusButton;
    }

    public JButton getRotateYPlusButton() {
        return rotateYPlusButton;
    }

    public JButton getRotateZMinusButton() {
        return rotateZMinusButton;
    }

    public JButton getRotateZPlusButton() {
        return rotateZPlusButton;
    }

}
