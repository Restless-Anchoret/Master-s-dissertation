package com.ran.engine.factories.world;

import com.ran.engine.factories.objects.DemonstrationFiguresFactory;
import com.ran.engine.factories.objects.FigureFactory;
import com.ran.engine.factories.objects.InterpolatedFiguresFactory;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;
import com.ran.engine.rendering.world.*;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BezierWorldFactory implements WorldFactory {

    private static final BezierWorldFactory INSTANCE = new BezierWorldFactory();

    public static BezierWorldFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public World createWorld() {
        FigureFactory figureFactory = FigureFactory.getInstance();
        InterpolatedFiguresFactory interpolatedFiguresFactory = InterpolatedFiguresFactory.getInstance();
        DemonstrationFiguresFactory demonstrationFiguresFactory = DemonstrationFiguresFactory.getInstance();

        List<ThreeDoubleVector> sphereCurveVertices = makeVerticesForInterpolationList();
        Orientation firstSphereOrientation = Orientation.createForOffset(-6.0, 0.0, 4.0);

        List<DisplayableObject> displayableObjects = Arrays.asList(
                new StaticObject(figureFactory.makePlainGrid(20, 8, 1.0, 1.0),
                        Orientation.INITIAL_ORIENTATION,
                        Color.LIGHT_GRAY),
                new StaticObject(figureFactory.makeGlobe(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, 3.0, 12),
                        firstSphereOrientation,
                        Color.LIGHT_GRAY, 1, 0),
                new StaticObject(demonstrationFiguresFactory.makeFigureByBigArcs(sphereCurveVertices, 20),
                        firstSphereOrientation,
                        Color.BLUE, 1.5f, 2),
                new StaticObject(interpolatedFiguresFactory.makeBezierCurveByMiddlePoints(
                        sphereCurveVertices, 1, 100),
                        firstSphereOrientation),
                new StaticObject(new Figure(sphereCurveVertices, Collections.EMPTY_LIST),
                        firstSphereOrientation, Color.RED, 0, 4)
        );
        Camera camera = Camera.createForPositionAndAngles(new ThreeDoubleVector(0.0, 10.0, 4.0), 0.0, 0.0);
        return new World(displayableObjects, camera);
    }

    private List<ThreeDoubleVector> makeVerticesForInterpolationList() {
        return Arrays.asList(
                new ThreeDoubleVector(0.0, 0.0, 3.0),
//                new ThreeDoubleVector(0.0, 3.0 / Math.sqrt(2.0), 3.0 / Math.sqrt(2.0)),
                new ThreeDoubleVector(0.0, 3.0, 0.0),
                new ThreeDoubleVector(3.0, 0.0, 0.0),
                new ThreeDoubleVector(0.0, 0.0, -3.0),
//                new ThreeDoubleVector(0.0, -3.0 / Math.sqrt(2.0), -3.0 / Math.sqrt(2.0)),
                new ThreeDoubleVector(0.0, -3.0, 0.0),
                new ThreeDoubleVector(-3.0, 0.0, 0.0)
        );
    }

}