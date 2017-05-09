package com.ran.engine.factories.world;

import com.ran.engine.factories.constants.TangentAnglesConstants;
import com.ran.engine.factories.objects.FigureFactory;
import com.ran.engine.factories.objects.InterpolatedFiguresFactory;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;
import com.ran.engine.rendering.world.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class TangentAnglesWorldFactory implements WorldFactory {

    private static final Logger LOG = LoggerFactory.getLogger(TangentAnglesWorldFactory.class);

    private static final TangentAnglesWorldFactory INSTANCE = new TangentAnglesWorldFactory();

    public static TangentAnglesWorldFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public World createWorld() {
        LOG.trace("TangentAnglesWorldFactory started creating");
        FigureFactory figureFactory = FigureFactory.getInstance();
        InterpolatedFiguresFactory interpolatedFiguresFactory = InterpolatedFiguresFactory.getInstance();
//        DemonstrationFiguresFactory demonstrationFiguresFactory = DemonstrationFiguresFactory.getInstance();

        List<Pair<ThreeDoubleVector, Double>> sphereCurveVerticesWithoutAngles = TangentAnglesConstants.getListWithoutTangentAngles();
        List<Pair<ThreeDoubleVector, Double>> sphereCurveVerticesWithSomeAngles = TangentAnglesConstants.getListWithSomeTangentAngles();
        List<Pair<ThreeDoubleVector, Double>> sphereCurveVerticesWithAllAngles = TangentAnglesConstants.getListWithAllTangentAngles();
        Orientation firstSphereOrientation = Orientation.createForOffset(-12.0, 0.0, 4.0);
        Orientation secondSphereOrientation = Orientation.createForOffset(0.0, 0.0, 4.0);
        Orientation thirdSphereOrientation = Orientation.createForOffset(12.0, 0.0, 4.0);

        List<DisplayableObject> displayableObjects = Arrays.asList(
                new StaticObject(figureFactory.makePlainGrid(32, 8, 1.0, 1.0),
                        Orientation.INITIAL_ORIENTATION,
                        Color.LIGHT_GRAY),
                new StaticObject(figureFactory.makeGlobe(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, 3.0, 12),
                        firstSphereOrientation,
                        Color.LIGHT_GRAY, 1, 0),
                new StaticObject(figureFactory.makeGlobe(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, 3.0, 12),
                        secondSphereOrientation,
                        Color.LIGHT_GRAY, 1, 0),
                new StaticObject(figureFactory.makeGlobe(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, 3.0, 12),
                        thirdSphereOrientation,
                        Color.LIGHT_GRAY, 1, 0),
//                new StaticObject(demonstrationFiguresFactory.makeFigureByBigArcs(sphereCurveVertices, 20),
//                        firstSphereOrientation,
//                        Color.BLUE, 1.5f, 2),
                new StaticObject(interpolatedFiguresFactory.makeInterpolatedCurveByTangentAngles(
                        sphereCurveVerticesWithoutAngles, 1, 100),
                        firstSphereOrientation),
                new StaticObject(interpolatedFiguresFactory.makeInterpolatedCurveByTangentAngles(
                        sphereCurveVerticesWithSomeAngles, 1, 100),
                        secondSphereOrientation),
                new StaticObject(interpolatedFiguresFactory.makeInterpolatedCurveByTangentAngles(
                        sphereCurveVerticesWithAllAngles, 1, 100),
                        thirdSphereOrientation)
//                new StaticObject(new Figure(sphereCurveVertices, Collections.EMPTY_LIST),
//                        firstSphereOrientation, Color.RED, 0, 4)
        );
        Camera camera = Camera.createForPositionAndAngles(new ThreeDoubleVector(0.0, 10.0, 4.0), 0.0, 0.0);
        LOG.trace("TangentAnglesWorldFactory finished creating");
        return new World(displayableObjects, camera);
    }

}