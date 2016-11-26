package com.ran.dissertation.labs.lab1;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.controller.*;
import com.ran.dissertation.labs.common.PlainCameraController;
import com.ran.dissertation.ui.MainFrame;
import com.ran.dissertation.world.Camera;
import com.ran.dissertation.world.World;

public class LabFirstMainController {

    private Camera camera;
    private World world;
    private MainFrame mainFrame;
    
    public void startApplication() {
        mainFrame = new MainFrame();
        int imagePanelWidth = mainFrame.getImagePanel().getWidth();
        camera = new Camera(new ThreeDoubleVector(0.0, 1.0, 1.0), 0.0, 12.0);
        world = LabFirstWorldFactory.getInstance().createWorldForCamera(camera, imagePanelWidth);
        DefaultPaintStrategy paintStrategy = new DefaultPaintStrategy(world);
        mainFrame.getImagePanel().setImagePanelPaintStrategy(paintStrategy);
        mainFrame.getImagePanel().addImagePanelListener(new PlainCameraController(world.getCamera(), paintStrategy));
        mainFrame.getAnimationControlPanel().setVisible(false);
        mainFrame.setVisible(true);
    }
    
}