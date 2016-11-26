package com.ran.dissertation.controller;

import com.ran.dissertation.factories.WorldFactory;
import com.ran.dissertation.ui.AnimationStrategy;
import com.ran.dissertation.ui.ImagePanel;
import com.ran.dissertation.ui.MainFrame;
import com.ran.dissertation.ui.SelectItem;
import com.ran.dissertation.world.AnimatedObject;
import com.ran.dissertation.world.World;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private final WorldFactory worldFactory;
    private World world;
    private MainFrame mainFrame;

    public MainController(WorldFactory worldFactory) {
        this.worldFactory = worldFactory;
    }
    
    public void startApplication() {
        world = worldFactory.createWorld();
        mainFrame = new MainFrame();
        mainFrame.getImagePanel().setImagePanelPaintStrategy(new DefaultPaintStrategy(world));
        mainFrame.getImagePanel().addImagePanelListener(new DefaultCameraController(world.getCamera()));
        mainFrame.getAnimationControlPanel().setAnimations(prepareAnimationSelectItemsForWorld(world, mainFrame.getImagePanel()));
        mainFrame.getAnimationControlPanel().addAnimationControlPanelListener(new DefaultAnimationController());
        mainFrame.setVisible(true);
    }
    
    private List<SelectItem<AnimationStrategy>> prepareAnimationSelectItemsForWorld(World world, ImagePanel imagePanel) {
        List<AnimatedObject> animatedObjects = world.getAnimatedObjects();
        List<SelectItem<AnimationStrategy>> selectItems = new ArrayList<>(animatedObjects.size());
        for (int i = 0; i < animatedObjects.size(); i++) {
            String animationName = "Animation #" + (i + 1);
            AnimationStrategy animationStrategy = new DefaultAnimationStrategy(animatedObjects.get(i), imagePanel);
            selectItems.add(new SelectItem<>(animationStrategy, animationName));
        }
        return selectItems;
    }
    
}