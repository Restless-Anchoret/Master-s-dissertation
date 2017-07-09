package com.ran.engine.factories.world;

import com.ran.engine.factories.constants.TangentAnglesConstants;
import com.ran.engine.factories.objects.DemonstrationFiguresFactory;
import com.ran.engine.factories.objects.FigureFactory;
import com.ran.engine.factories.objects.InterpolatedFiguresFactory;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;
import com.ran.engine.rendering.world.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.ran.engine.factories.constants.TangentAnglesConstants.DARK_GRAY_COLOR;

public class TangentAnglesWorldFactory implements WorldFactory {

    private static final Logger LOG = LoggerFactory.getLogger(TangentAnglesWorldFactory.class);

    private static final TangentAnglesWorldFactory INSTANCE = new TangentAnglesWorldFactory();

    public static TangentAnglesWorldFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public World createWorld() {
        LOG.trace("TangentAnglesWorldFactory started creating");
        List<Pair<ThreeDoubleVector, Double>> sphereCurveVerticesWithoutAngles = TangentAnglesConstants.getListWithoutTangentAngles();
        List<Pair<ThreeDoubleVector, Double>> sphereCurveVerticesWithSomeAngles = TangentAnglesConstants.getListWithSomeTangentAngles();
        List<Pair<ThreeDoubleVector, Double>> sphereCurveVerticesWithAllAngles = TangentAnglesConstants.getListWithAllTangentAngles();
        List<Pair<ThreeDoubleVector, Double>> sphereCurveCloseVerticesWithoutAngles = TangentAnglesConstants.getCloseListWithoutTangentAngles();
        List<Pair<ThreeDoubleVector, Double>> sphereCurveCloseVerticesWithSomeAngles = TangentAnglesConstants.getCloseListWithSomeTangentAngles();

        Orientation firstSphereOrientation = Orientation.createForOffset(-12.0, 0.0, 4.0);
        Orientation secondSphereOrientation = Orientation.createForOffset(0.0, 0.0, 4.0);
        Orientation thirdSphereOrientation = Orientation.createForOffset(12.0, 0.0, 4.0);
        Orientation firstCloseSphereOrientation = Orientation.createForOffset(-12.0, 0.0, -8.0);
        Orientation secondCloseSphereOrientation = Orientation.createForOffset(0.0, 0.0, -8.0);

        List<DisplayableObject> displayableObjects = new ArrayList<>();
//        displayableObjects.add(new StaticObject(figureFactory.makePlainGrid(32, 8, 1.0, 1.0),
//                Orientation.INITIAL_ORIENTATION, Color.LIGHT_GRAY));

        displayableObjects.addAll(createDisplayableObjectsGroup(
                sphereCurveVerticesWithoutAngles, firstSphereOrientation));
        displayableObjects.addAll(createDisplayableObjectsGroup(
                sphereCurveVerticesWithSomeAngles, secondSphereOrientation));
        displayableObjects.addAll(createDisplayableObjectsGroup(
                sphereCurveVerticesWithAllAngles, thirdSphereOrientation));

        displayableObjects.addAll(createDisplayableObjectsGroup(
                sphereCurveCloseVerticesWithoutAngles, firstCloseSphereOrientation));
        displayableObjects.addAll(createDisplayableObjectsGroup(
                sphereCurveCloseVerticesWithSomeAngles, secondCloseSphereOrientation));

        Camera camera = Camera.createForPositionAndAngles(new ThreeDoubleVector(0.0, 10.0, 4.0), 0.0, 0.0);
        LOG.trace("TangentAnglesWorldFactory finished creating");
        return new World(displayableObjects, camera);
    }

    private List<DisplayableObject> createDisplayableObjectsGroup(
            List<Pair<ThreeDoubleVector, Double>> verticesWithAnglesList,
            Orientation orientation) {
        FigureFactory figureFactory = FigureFactory.getInstance();
        InterpolatedFiguresFactory interpolatedFiguresFactory = InterpolatedFiguresFactory.getInstance();

        List<DisplayableObject> displayableObjects = new ArrayList<>();
        displayableObjects.add(new StaticObject(figureFactory.makeGlobe(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, 3.0, 12),
                        orientation, DARK_GRAY_COLOR, 1, 0));
        displayableObjects.add(new StaticObject(interpolatedFiguresFactory.makeInterpolatedCurveByTangentAngles(
                        verticesWithAnglesList, 2, 150), orientation));
//        displayableObjects.addAll(createSmallArcs(verticesWithAnglesList, orientation));
//        displayableObjects.addAll(createBigArcs(verticesWithAnglesList, orientation));
        displayableObjects.addAll(createTangents(verticesWithAnglesList, orientation));

        List<ThreeDoubleVector> vertices = verticesWithAnglesList.stream()
                .map(Pair::getLeft).collect(Collectors.toList());
        displayableObjects.add(new StaticObject(new Figure(vertices, Collections.emptyList()),
                orientation, Color.RED, 0, 5));
        return displayableObjects;
    }

//    private List<DisplayableObject> createSmallArcs(
//            List<Pair<ThreeDoubleVector, Double>> verticesWithAnglesList,
//            Orientation orientation) {
//        DemonstrationFiguresFactory demonstrationFiguresFactory = DemonstrationFiguresFactory.getInstance();
//        return Collections.emptyList();
//    }
//
//    private List<DisplayableObject> createBigArcs(
//            List<Pair<ThreeDoubleVector, Double>> verticesWithAnglesList,
//            Orientation orientation) {
//        DemonstrationFiguresFactory demonstrationFiguresFactory = DemonstrationFiguresFactory.getInstance();
//        return Collections.emptyList();
//    }

    private List<DisplayableObject> createTangents(
            List<Pair<ThreeDoubleVector, Double>> verticesWithAnglesList,
            Orientation orientation) {
        DemonstrationFiguresFactory demonstrationFiguresFactory = DemonstrationFiguresFactory.getInstance();
        List<DisplayableObject> displayableObjects = new ArrayList<>();
        for (Pair<ThreeDoubleVector, Double> pair: verticesWithAnglesList) {
            if (pair.getRight() != null) {
                displayableObjects.add(new StaticObject(
                        demonstrationFiguresFactory.makeTangentOnSphereByPoint(
                                pair.getLeft(), pair.getRight(), 6),
                        orientation, Color.BLUE));
            }
        }
        return displayableObjects;
    }

}