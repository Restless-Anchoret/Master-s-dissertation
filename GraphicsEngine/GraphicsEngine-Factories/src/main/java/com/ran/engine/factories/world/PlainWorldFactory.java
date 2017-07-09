package com.ran.engine.factories.world;

import com.ran.engine.factories.objects.FigureFactory;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;
import com.ran.engine.rendering.world.*;

import java.util.Arrays;
import java.util.List;

import static com.ran.engine.factories.constants.TangentAnglesConstants.DARK_GRAY_COLOR;

public class PlainWorldFactory implements WorldFactory {

    private static final PlainWorldFactory INSTANCE = new PlainWorldFactory();

    public static PlainWorldFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public World createWorld() {
        FigureFactory figureFactory = FigureFactory.getInstance();

        List<DisplayableObject> displayableObjects = Arrays.asList(
                new StaticObject(figureFactory.makeVerticalPlainGrid(20, 16, 1.0, 1.0),
                        Orientation.createForOffset(-30.0, 0.0, 0.0),
                        DARK_GRAY_COLOR),
                new StaticObject(figureFactory.makeVerticalPlainGrid(20, 16, 1.0, 1.0),
                        Orientation.INITIAL_ORIENTATION,
                        DARK_GRAY_COLOR),
                new StaticObject(figureFactory.makeVerticalPlainGrid(20, 16, 1.0, 1.0),
                        Orientation.createForOffset(30.0, 0.0, 0.0),
                        DARK_GRAY_COLOR)
                // todo: add some objects
        );
        Camera camera = Camera.createForPositionAndAngles(new ThreeDoubleVector(0.0, 10.0, 0.0), 0.0, 0.0);
        return new World(displayableObjects, camera);
    }

}