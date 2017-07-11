package com.ran.engine.factories.world;

import com.ran.engine.factories.constants.PlanePointsConstants;
import com.ran.engine.factories.objects.DemonstrationFiguresFactory;
import com.ran.engine.factories.objects.FigureFactory;
import com.ran.engine.factories.objects.InterpolatedFiguresFactory;
import com.ran.engine.factories.util.CoordinatesConverter;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;
import com.ran.engine.rendering.algebraic.vector.TwoDoubleVector;
import com.ran.engine.rendering.world.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.ran.engine.factories.constants.TangentAnglesConstants.DARK_GRAY_COLOR;

public class PlaneWorldFactory implements WorldFactory {

    private static final PlaneWorldFactory INSTANCE = new PlaneWorldFactory();

    public static PlaneWorldFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public World createWorld() {
        FigureFactory figureFactory = FigureFactory.getInstance();
        InterpolatedFiguresFactory interpolatedFiguresFactory = InterpolatedFiguresFactory.getInstance();
        DemonstrationFiguresFactory demonstrationFiguresFactory = DemonstrationFiguresFactory.getInstance();

        Orientation simpleInterpolationOrientation = Orientation.createForOffset(-30.0, 0.0, 0.0);
        Orientation bezierInterpolationOrientation = Orientation.INITIAL_ORIENTATION;
        Orientation tangentInterpolationOrientation = Orientation.createForOffset(30.0, 0.0, 0.0);

        List<TwoDoubleVector> simpleInterpolationVertices = PlanePointsConstants.getListForSimpleInterpolation();

        List<DisplayableObject> displayableObjects = Arrays.asList(
                // Interpolation by points
                new StaticObject(figureFactory.makeVerticalPlainGrid(20, 16, 1.0, 1.0),
                        simpleInterpolationOrientation, DARK_GRAY_COLOR),
                new StaticObject(demonstrationFiguresFactory.makeFigureByCircleArcs(
                        simpleInterpolationVertices, 20),
                        simpleInterpolationOrientation, Color.BLUE),
                new StaticObject(interpolatedFiguresFactory.makePlaneInterpolatedCurveByPoints(
                        simpleInterpolationVertices, 2, 200),
                        simpleInterpolationOrientation, Color.BLACK),
                new StaticObject(demonstrationFiguresFactory.makeFigureByPoints(
                        simpleInterpolationVertices, CoordinatesConverter.CONVERTER_TO_XZ),
                        simpleInterpolationOrientation, Color.RED, 0, 7),

                // Bezier interpolation
                new StaticObject(figureFactory.makeVerticalPlainGrid(20, 16, 1.0, 1.0),
                        bezierInterpolationOrientation, DARK_GRAY_COLOR),
                // todo: add some objects

                // Interpolation by tangent angles
                new StaticObject(figureFactory.makeVerticalPlainGrid(20, 16, 1.0, 1.0),
                        tangentInterpolationOrientation, DARK_GRAY_COLOR)
                // todo: add some objects
        );
        Camera camera = Camera.createForPositionAndAngles(new ThreeDoubleVector(0.0, 10.0, 0.0), 0.0, 0.0);
        return new World(displayableObjects, camera);
    }

}