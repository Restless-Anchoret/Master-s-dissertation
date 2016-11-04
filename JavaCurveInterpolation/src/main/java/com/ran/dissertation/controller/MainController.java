package com.ran.dissertation.controller;

import com.ran.dissertation.factories.WorldFactory;
import com.ran.dissertation.ui.MainFrame;
import com.ran.dissertation.world.World;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainController {

    private final WorldFactory worldFactory;
    private World world;
    private MainFrame mainFrame;

    public MainController(WorldFactory worldFactory) {
        this.worldFactory = worldFactory;
    }
    
    public void startApplication() {
        world = worldFactory.createWorld();
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
        mainFrame = new MainFrame();
        mainFrame.getImagePanel().setImagePanelPaintStrategy(new DefaultPaintStrategy(world));
        mainFrame.getImagePanel().addImagePanelListener(new DefaultCameraController(world.getCamera()));
        mainFrame.setVisible(true);
    }
    
}