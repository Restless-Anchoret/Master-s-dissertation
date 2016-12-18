package com.ran.dissertation.labs.lab3;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.controller.*;
import com.ran.dissertation.factories.AffineTransformationFactory;
import com.ran.dissertation.factories.FigureFactory;
import com.ran.dissertation.ui.MainFrame;
import com.ran.dissertation.ui.PresentationComboBox;
import com.ran.dissertation.ui.SelectItem;
import com.ran.dissertation.world.AffineTransformation;
import com.ran.dissertation.world.DisplayableObject;
import com.ran.dissertation.world.Orientation;
import com.ran.dissertation.world.World;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LabThirdMainController {

    private static final double OFFSET_STEP = 0.25;
    private static final double ROTATION_STEP = Math.PI / 18.0;
    private static final double SCALE_STEP = 1.1;
    
    private World world;
    private MainFrame mainFrame;
    private LabThirdDialogPanelContent dialogPanelContent;

    public void startApplication() {
        world = LabThirdWorldFactory.getInstance().createWorld();
        tryToSetBetterLaf();
        mainFrame = new MainFrame();
        mainFrame.getImagePanel().setImagePanelPaintStrategy(new DefaultPaintStrategy(world));
        mainFrame.getImagePanel().addImagePanelListener(new DefaultCameraController(world.getCamera()));
        dialogPanelContent = prepareDialogPanelContent();
        mainFrame.getDialogPanel().setComponent(dialogPanelContent);
        mainFrame.setVisible(true);
    }

    private void tryToSetBetterLaf() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException exception) {
            exception.printStackTrace();
        }
    }
    
    private LabThirdDialogPanelContent prepareDialogPanelContent() {
        AffineTransformationFactory factory = AffineTransformationFactory.getInstance();
        LabThirdDialogPanelContent dialogPanelContent = new LabThirdDialogPanelContent();
        ThreeDimensionAffineDialog affineDialog = dialogPanelContent.getThreeDimensionAffineDialog();
        
        prepareComboBox(affineDialog.getChosenVerticeComboBox());
        
        assignActionListener(affineDialog.getMoveXPlusButton(),
                factory.createOffsetAffineTransformation(new ThreeDoubleVector(OFFSET_STEP, 0.0, 0.0)));
        assignActionListener(affineDialog.getMoveXMinusButton(),
                factory.createOffsetAffineTransformation(new ThreeDoubleVector(-OFFSET_STEP, 0.0, 0.0)));
        assignActionListener(affineDialog.getMoveYPlusButton(),
                factory.createOffsetAffineTransformation(new ThreeDoubleVector(0.0, OFFSET_STEP, 0.0)));
        assignActionListener(affineDialog.getMoveYMinusButton(),
                factory.createOffsetAffineTransformation(new ThreeDoubleVector(0.0, -OFFSET_STEP, 0.0)));
        assignActionListener(affineDialog.getMoveZPlusButton(),
                factory.createOffsetAffineTransformation(new ThreeDoubleVector(0.0, 0.0, OFFSET_STEP)));
        assignActionListener(affineDialog.getMoveZMinusButton(),
                factory.createOffsetAffineTransformation(new ThreeDoubleVector(0.0, 0.0, -OFFSET_STEP)));
        
        assignRotationActionListener(affineDialog.getRotateXPlusButton(),
                factory.createXAxisRotationAffineTransformation(ROTATION_STEP),
                factory::createXAxisRotationOverPointAffineTransformation, ROTATION_STEP);
        assignRotationActionListener(affineDialog.getRotateXMinusButton(),
                factory.createXAxisRotationAffineTransformation(-ROTATION_STEP),
                factory::createXAxisRotationOverPointAffineTransformation, -ROTATION_STEP);
        assignRotationActionListener(affineDialog.getRotateYPlusButton(),
                factory.createYAxisRotationAffineTransformation(ROTATION_STEP),
                factory::createYAxisRotationOverPointAffineTransformation, ROTATION_STEP);
        assignRotationActionListener(affineDialog.getRotateYMinusButton(),
                factory.createYAxisRotationAffineTransformation(-ROTATION_STEP),
                factory::createYAxisRotationOverPointAffineTransformation, -ROTATION_STEP);
        assignRotationActionListener(affineDialog.getRotateZPlusButton(),
                factory.createZAxisRotationAffineTransformation(ROTATION_STEP),
                factory::createZAxisRotationOverPointAffineTransformation, ROTATION_STEP);
        assignRotationActionListener(affineDialog.getRotateZMinusButton(),
                factory.createZAxisRotationAffineTransformation(-ROTATION_STEP),
                factory::createZAxisRotationOverPointAffineTransformation, -ROTATION_STEP);
        
        assignActionListener(affineDialog.getReflectXButton(), factory.createXAxisReflectionAffineTransformation());
        assignActionListener(affineDialog.getReflectYButton(), factory.createYAxisReflectionAffineTransformation());
        assignActionListener(affineDialog.getReflectZButton(), factory.createZAxisReflectionAffineTransformation());
        assignActionListener(affineDialog.getReflectXoYButton(), factory.createXOYReflectionAffineTransformation());
        assignActionListener(affineDialog.getReflectXoZButton(), factory.createXOZReflectionAffineTransformation());
        assignActionListener(affineDialog.getReflectYoZButton(), factory.createYOZReflectionAffineTransformation());
        assignActionListener(affineDialog.getReflectPivotButton(), factory.createCenterReflectionAffineTransformation());
        
        assignActionListener(affineDialog.getIncreaseButton(), factory.createScaleAffineTransformation(SCALE_STEP));
        assignActionListener(affineDialog.getDecreaseButton(), factory.createScaleAffineTransformation(1.0 / SCALE_STEP));
        
        return dialogPanelContent;
    }
    
    private void prepareComboBox(PresentationComboBox<Integer> comboBox) {
        int verticesQuantity = world.getDisplayableObjects().get(2)
                .getFigure().getVertices().size();
        List<SelectItem<Integer>> selectItems = new ArrayList<>(verticesQuantity + 1);
        selectItems.add(new SelectItem<>(null, "Pivot"));
        for (int i = 0; i < verticesQuantity; i++) {
            selectItems.add(new SelectItem<>(i, "Vertice #" + (i + 1)));
        }
        comboBox.setSelectItems(selectItems);
        comboBox.getComboBox().addItemListener(event -> {
            updateChosenVerticeLocation();
            mainFrame.getImagePanel().repaint();
        });
    }
    
    private void assignActionListener(JButton button, AffineTransformation affineTransformation) {
        button.addActionListener(event -> {
            List<DisplayableObject> displayableObjects = world.getDisplayableObjects();
            for (int i = 1; i <= 2; i++) {
                affineTransformation.performTransformation(displayableObjects.get(i));
            }
            updateChosenVerticeLocation();
            mainFrame.getImagePanel().repaint();
        });
    }
    
    private void assignRotationActionListener(JButton button, AffineTransformation defaultAffineTransformation,
            BiFunction<ThreeDoubleVector, Double, AffineTransformation> factoryMethod, double angle) {
        button.addActionListener(event -> {
            Integer chosenVerticeIndex = dialogPanelContent.getThreeDimensionAffineDialog()
                    .getChosenVerticeComboBox().getSelectedValue();
            ThreeDoubleVector point = (chosenVerticeIndex == null ? null :
                    world.getDisplayableObjects().get(2)
                            .getCurrentFigureVertices().get(chosenVerticeIndex));
            AffineTransformation pointRotationAffineTransformation = (point == null ? defaultAffineTransformation :
                    factoryMethod.apply(point, angle));
            List<DisplayableObject> displayableObjects = world.getDisplayableObjects();
            for (int i = 1; i <= 2; i++) {
                pointRotationAffineTransformation.performTransformation(displayableObjects.get(i));
            }
            mainFrame.getImagePanel().repaint();
        });
    }
    
    private void updateChosenVerticeLocation() {
        FigureFactory figureFactory = FigureFactory.getInstance();
        Integer chosenVerticeIndex = dialogPanelContent.getThreeDimensionAffineDialog()
                .getChosenVerticeComboBox().getSelectedValue();
        DisplayableObject displayableObject = world.getDisplayableObjects().get(2);
        ThreeDoubleVector vertice = (chosenVerticeIndex == null ? displayableObject.getOrientation().getOffset() :
                displayableObject.getCurrentFigureVertices().get(chosenVerticeIndex));
        world.getDisplayableObjects().set(3, new DisplayableObject(
                figureFactory.makeFigureWithOneVertice(vertice),
                Orientation.INITIAL_ORIENTATION, Color.RED, 1.0f, 3));
    }

}
