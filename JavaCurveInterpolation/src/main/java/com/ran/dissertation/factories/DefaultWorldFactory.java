package com.ran.dissertation.factories;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.world.AnimatedObject;
import com.ran.dissertation.world.Camera;
import com.ran.dissertation.world.DisplayableObject;
import com.ran.dissertation.world.Orientation;
import com.ran.dissertation.world.World;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class DefaultWorldFactory implements WorldFactory {

    private static final DefaultWorldFactory INSTANCE = new DefaultWorldFactory();

    public static DefaultWorldFactory getInstance() {
        return INSTANCE;
    }
    
    private DefaultWorldFactory() { }

    @Override
    public World createWorld() {
        FigureFactory figureFactory = FigureFactory.getInstance();
        AnimationFactory animationFactory = AnimationFactory.getInstance();
        List<DisplayableObject> displayableObjects = Arrays.asList(
                new DisplayableObject(figureFactory.makePlainGrid(8, 8, 1.0, 1.0),
                        Orientation.INITIAL_ORIENTATION, Color.GRAY),
                new DisplayableObject(figureFactory.makeGrid(4, 4, 1, 2.0, 2.0, 2.0),
                        Orientation.createForOffset(new ThreeDoubleVector(12.0, 0.0, 1.0))),
                new DisplayableObject(figureFactory.makeCube(2.0),
                        Orientation.createForOffset(new ThreeDoubleVector(-3.0, -3.0, 2.0)))
        );
        List<AnimatedObject> animatedObjects = Arrays.asList(
                new AnimatedObject(figureFactory.makeCube(2.0),
                        animationFactory.makeZRotationAnimation(new ThreeDoubleVector(3.0, -3.0, 2.0), 100))
        );
        Camera camera = new Camera();
        return new World(displayableObjects, animatedObjects, camera);
    }
    
}