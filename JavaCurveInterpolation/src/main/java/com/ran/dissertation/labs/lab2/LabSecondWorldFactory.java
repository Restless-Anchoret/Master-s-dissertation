package com.ran.dissertation.labs.lab2;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.factories.FigureFactory;
import com.ran.dissertation.factories.WorldFactory;
import com.ran.dissertation.world.AnimatedObject;
import com.ran.dissertation.world.Camera;
import com.ran.dissertation.world.DisplayableObject;
import com.ran.dissertation.world.Orientation;
import com.ran.dissertation.world.World;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class LabSecondWorldFactory implements WorldFactory {

    private static final LabSecondWorldFactory INSTANCE = new LabSecondWorldFactory();

    public static LabSecondWorldFactory getInstance() {
        return INSTANCE;
    }
    
    private LabSecondWorldFactory() { }

    @Override
    public World createWorld() {
        FigureFactory figureFactory = FigureFactory.getInstance();
        List<DisplayableObject> displayableObjects = Arrays.asList(
                new DisplayableObject(figureFactory.makeGrid(40, 0, 40, 1.0, 0.0, 1.0),
                        Orientation.INITIAL_ORIENTATION,
                        Color.LIGHT_GRAY),
                new DisplayableObject(figureFactory.makeGrid(5, 0, 5, 1.5, 0.0, 1.5,
                        new ThreeDoubleVector(1.0, 0.0, 1.0))),
                new DisplayableObject(figureFactory.makeFigureWithOneVertice(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR),
                        Orientation.INITIAL_ORIENTATION, Color.RED, 1.0f, 3)
        );
        List<AnimatedObject> animatedObjects = Arrays.asList();
        Camera camera = Camera.createForPositionReversedDistanceAndLensWidth(
                new ThreeDoubleVector(0.0, 1.0, 1.0), 0.0, 20.0);
        return new World(displayableObjects, animatedObjects, camera);
    }
    
}