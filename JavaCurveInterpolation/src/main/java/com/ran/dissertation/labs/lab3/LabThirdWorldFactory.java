package com.ran.dissertation.labs.lab3;

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

public class LabThirdWorldFactory implements WorldFactory {

    private static final LabThirdWorldFactory INSTANCE = new LabThirdWorldFactory();

    public static LabThirdWorldFactory getInstance() {
        return INSTANCE;
    }
    
    private LabThirdWorldFactory() { }

    @Override
    public World createWorld() {
        FigureFactory figureFactory = FigureFactory.getInstance();
        List<DisplayableObject> displayableObjects = Arrays.asList(
                new DisplayableObject(figureFactory.makePlainGrid(20, 20, 1.0, 1.0),
                        Orientation.INITIAL_ORIENTATION,
                        Color.LIGHT_GRAY),
                new DisplayableObject(figureFactory.makeGlobe(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, 3.0, 12),
                        Orientation.createForOffset(0.0, 0.0, 4.0),
                        Color.LIGHT_GRAY, 1, 0),
                new DisplayableObject(figureFactory.makeInterpolatedCurve(makeVerticesForInterpolationList(), 1, 100),
                        Orientation.createForOffset(0.0, 0.0, 4.0)),
                new DisplayableObject(figureFactory.makeFigureWithOneVertice(new ThreeDoubleVector(0.0, 0.0, 4.0)),
                        Orientation.INITIAL_ORIENTATION, Color.RED, 1.0f, 3)
        );
        List<AnimatedObject> animatedObjects = Arrays.asList();
        Camera camera = Camera.createForPositionAndAngles(new ThreeDoubleVector(0.0, 10.0, 4.0), 0.0, 0.0);
        return new World(displayableObjects, animatedObjects, camera);
    }
    
    private List<ThreeDoubleVector> makeVerticesForInterpolationList() {
        return Arrays.asList(
                new ThreeDoubleVector(0.0, 0.0, 3.0),
                new ThreeDoubleVector(0.0, 3.0, 0.0),
                new ThreeDoubleVector(3.0, 0.0, 0.0),
                new ThreeDoubleVector(0.0, 0.0, -3.0),
                new ThreeDoubleVector(0.0, -3.0, 0.0),
                new ThreeDoubleVector(-3.0, 0.0, 0.0)
        );
    }
    
}