package com.ran.engine.factories.world;

import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.vector.ThreeDoubleVector;
import com.ran.engine.factories.constants.TangentAnglesConstants;
import com.ran.engine.rendering.world.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TangentAnglesWorldFactory extends BaseWorldFactory {

    private static final Logger LOG = LoggerFactory.getLogger(TangentAnglesWorldFactory.class);

    @Override
    protected List<WorldObjectCreator> getWorldObjectCreators() {
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

        List<WorldObjectCreator> worldObjectCreators = new ArrayList<>();

        worldObjectCreators.addAll(getWorldObjectCreatorsGroup(
                sphereCurveVerticesWithoutAngles, firstSphereOrientation));
        worldObjectCreators.addAll(getWorldObjectCreatorsGroup(
                sphereCurveVerticesWithSomeAngles, secondSphereOrientation));
        worldObjectCreators.addAll(getWorldObjectCreatorsGroup(
                sphereCurveVerticesWithAllAngles, thirdSphereOrientation));

        worldObjectCreators.addAll(getWorldObjectCreatorsGroup(
                sphereCurveCloseVerticesWithoutAngles, firstCloseSphereOrientation));
        worldObjectCreators.addAll(getWorldObjectCreatorsGroup(
                sphereCurveCloseVerticesWithSomeAngles, secondCloseSphereOrientation));

        LOG.trace("TangentAnglesWorldFactory finished creating");
        return worldObjectCreators;
    }

    @Override
    protected Camera getCamera() {
        return Camera.createForPositionAndAngles(new ThreeDoubleVector(0.0, 10.0, 4.0), 0.0, 0.0);
    }

    private List<WorldObjectCreator> getWorldObjectCreatorsGroup(
            List<Pair<ThreeDoubleVector, Double>> verticesWithAnglesList,
            Orientation orientation) {
        List<WorldObjectCreator> worldObjectCreators = new ArrayList<>();
        worldObjectCreators.add(fixedObjectCreator(createStandardGlobe(3.0), orientation));
        worldObjectCreators.add(fixedObjectCreator(new WorldObjectPartBuilder()
                .setFigure(getInterpolatedFiguresFactory().makeInterpolatedCurveByTangentAngles(
                        verticesWithAnglesList, 2, 150)).build(),
                orientation));
        worldObjectCreators.addAll(createSmallArcs(verticesWithAnglesList, orientation));
        worldObjectCreators.addAll(createBigArcs(verticesWithAnglesList, orientation));
        worldObjectCreators.addAll(createTangents(verticesWithAnglesList, orientation));

        List<ThreeDoubleVector> vertices = verticesWithAnglesList.stream()
                .map(Pair::getLeft).collect(Collectors.toList());
        worldObjectCreators.add(fixedObjectCreator(new WorldObjectPartBuilder()
                .setFigure(getFigureFactory().makeFigureByPoints(vertices))
                .setColor(Color.RED).setVerticePaintRadius(5).build(),
                orientation));
        return worldObjectCreators;
    }

    private List<WorldObjectCreator> createSmallArcs(
            List<Pair<ThreeDoubleVector, Double>> verticesWithAnglesList,
            Orientation orientation) {
        return Collections.emptyList();
    }

    private List<WorldObjectCreator> createBigArcs(
            List<Pair<ThreeDoubleVector, Double>> verticesWithAnglesList,
            Orientation orientation) {
        return Collections.emptyList();
    }

    private List<WorldObjectCreator> createTangents(
            List<Pair<ThreeDoubleVector, Double>> verticesWithAnglesList,
            Orientation orientation) {
        List<WorldObjectCreator> displayableObjects = new ArrayList<>();
        for (Pair<ThreeDoubleVector, Double> pair: verticesWithAnglesList) {
            if (pair.getRight() != null) {
                WorldObjectPart worldObjectPart = new WorldObjectPartBuilder()
                        .setFigure(getDemonstrationFiguresFactory().makeTangentOnSphereByPoint(
                                pair.getLeft(), pair.getRight(), 6))
                        .setColor(Color.BLUE).build();
                displayableObjects.add(fixedObjectCreator(worldObjectPart, orientation));
            }
        }
        return displayableObjects;
    }

}
