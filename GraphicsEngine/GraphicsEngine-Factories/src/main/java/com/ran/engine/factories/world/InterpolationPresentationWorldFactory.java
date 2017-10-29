package com.ran.engine.factories.world;

import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.quaternion.Quaternion;
import com.ran.engine.algebra.vector.ThreeDoubleVector;
import com.ran.engine.factories.animations.AffineTransformationFactory;
import com.ran.engine.factories.util.CoordinatesConverter;
import com.ran.engine.rendering.world.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InterpolationPresentationWorldFactory extends BaseWorldFactory {

    @Override
    protected Camera getCamera() {
        // Camera for plain curve interpolation presentation
//        return Camera.createForPositionAndAngles(new ThreeDoubleVector(0.0, 6.0, 0.0), 0.0, 0.0);

        // Camera for sphere curve interpolation presentation
        return Camera.createForPositionAndAngles(new ThreeDoubleVector(31.0, 7.0, 5.5), 0.0, 0.7);

        // Camera for orientation curve interpolation presentation
//        return Camera.createForPositionAndAngles(new ThreeDoubleVector(54.5, 10.0, 2.5), -0.5, 0.3);
    }

    @Override
    protected List<WorldObjectCreator> getWorldObjectCreators() {
        List<Pair<Double, Double>> pointsWithValues = makePointsWithValuesForInterpolationList();
        List<ThreeDoubleVector> sphereCurveVertices = makeVerticesForInterpolationList();
        List<Quaternion> quaternions = makeQuaternionsForInterpolationList();

        Orientation sphereOrientation = Orientation.createForOffset(30.0, 0.0, 0.0);
        Orientation animationOrientation = Orientation.createForOffset(60.0, 0.0, 0.0);

        List<WorldObjectCreator> worldObjectCreators = new ArrayList<>();
        worldObjectCreators.addAll(Arrays.asList(
                // Plain curve interpolation
                plainGridCreator(20, 16, 1.0, 1.0,
                        Orientation.INITIAL_ORIENTATION, true),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(getFigureFactory()
                        .makeGrid(20, 0, 0, 1.0, 0.0, 0.0)).build(),
                        Orientation.INITIAL_ORIENTATION),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(getFigureFactory()
                        .makeGrid(0, 0, 16, 0.0, 0.0, 1.0)).build(),
                        Orientation.INITIAL_ORIENTATION),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(getDemonstrationFiguresFactory()
                        .makeFigureByParabolas(pointsWithValues, 20, CoordinatesConverter.CONVERTER_TO_XZ))
                        .setColor(Color.BLUE).setEdgePaintWidth(1.5f).setVerticePaintRadius(2).build(),
                        Orientation.INITIAL_ORIENTATION),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(getInterpolatedFiguresFactory()
                        .makeSpline(pointsWithValues, 1, 140, CoordinatesConverter.CONVERTER_TO_XZ))
                        .setEdgePaintWidth(1.5f).setVerticePaintRadius(2).build(),
                        Orientation.INITIAL_ORIENTATION),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(getFigureFactory()
                        .makeFigureByPointsWithValues(pointsWithValues, CoordinatesConverter.CONVERTER_TO_XZ))
                        .setColor(Color.RED).setEdgePaintWidth(0f).setVerticePaintRadius(7).build(),
                        Orientation.INITIAL_ORIENTATION),

                // Sphere curve interpolation
                fixedObjectCreator(Arrays.asList(
                        createStandardGlobe(5.0),
                        new WorldObjectPartBuilder()
                                .setFigure(getDemonstrationFiguresFactory().makeFigureByArcs(sphereCurveVertices, 20))
                                .setColor(Color.BLUE).setEdgePaintWidth(1.5f).setVerticePaintRadius(2).build(),
                        new WorldObjectPartBuilder()
                                .setFigure(getInterpolatedFiguresFactory().makeInterpolatedCurve(sphereCurveVertices, 1, 100))
                                .setEdgePaintWidth(1.5f).setVerticePaintRadius(2).build(),
                        new WorldObjectPartBuilder()
                                .setFigure(getFigureFactory().makeFigureByPoints(sphereCurveVertices))
                                .setColor(Color.RED).setEdgePaintWidth(1.5f).setVerticePaintRadius(2).build()
                ), sphereOrientation)
        ));

        // Orientation curve interpolation
        worldObjectCreators.addAll(animationPresentationObjectCreators(
                animationOrientation, quaternions, 15));

        return worldObjectCreators;
    }
    
    private List<ThreeDoubleVector> makeVerticesForInterpolationList() {
        return Arrays.asList(
                new ThreeDoubleVector(-5.0, 0.0, 0.0),
                new ThreeDoubleVector(0.0, 0.0, 5.0),
                new ThreeDoubleVector(0.0, 5.0, 0.0),
                new ThreeDoubleVector(5.0, 0.0, 0.0)
        );
    }
    
    private ThreeDoubleVector getVectorForAngles(double angleXOY, double angleZ, double radius) {
        return new ThreeDoubleVector(Math.sin(angleXOY), Math.cos(angleXOY), Math.tan(angleZ)).normalized().multiply(radius);
    }

    // todo: remove this method
    private List<Orientation> makeOrientationsForInterpolationList() {
//        List<Orientation> orientations = Arrays.asList(
//                Orientation.INITIAL_ORIENTATION,
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI / 2.0),
//                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), Math.PI / 2.0),
//                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), Math.PI),
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), -Math.PI / 2.0),
//                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), -Math.PI / 2.0),
//                Orientation.INITIAL_ORIENTATION
//        );
//        List<Orientation> orientations = Arrays.asList(
//                Orientation.INITIAL_ORIENTATION,
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI / 2.0),
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI),
//                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), Math.PI / 2.0),
//                Orientation.INITIAL_ORIENTATION,
//                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), -Math.PI / 2.0),
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), -Math.PI / 2.0),
//                Orientation.INITIAL_ORIENTATION
//        );
//        List<Orientation> orientations = Arrays.asList(
//                Orientation.INITIAL_ORIENTATION,
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI / 2.0),
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI),
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), -Math.PI / 2.0),
//                Orientation.INITIAL_ORIENTATION
//        );
        List<Orientation> orientations = new ArrayList<>();
        List<AffineTransformation> affineTransformations = makeAffineTransformationsList();
        orientations.add(Orientation.INITIAL_ORIENTATION);
        for (AffineTransformation affineTransformation: affineTransformations) {
            Orientation lastOrientation = orientations.get(orientations.size() - 1);
            orientations.add(affineTransformation.apply(lastOrientation));
        }
        return orientations;
    }

    private List<AffineTransformation> makeAffineTransformationsList() {
        AffineTransformationFactory affineTransformationFactory = getAffineTransformationFactory();
//        List<AffineTransformation> affineTransformations = Arrays.asList(
//                affineTransformationFactory.createXAxisRotationAffineTransformation(Math.PI / 4.0),
//                affineTransformationFactory.createXAxisRotationAffineTransformation(Math.PI / 4.0),
//                affineTransformationFactory.createZAxisRotationAffineTransformation(Math.PI / 2.0),
//                affineTransformationFactory.createYAxisRotationAffineTransformation(-Math.PI / 2.0),
//                affineTransformationFactory.createXAxisRotationAffineTransformation(Math.PI / 4.0),
//                affineTransformationFactory.createXAxisRotationAffineTransformation(Math.PI / 4.0),
//                affineTransformationFactory.createZAxisRotationAffineTransformation(Math.PI / 4.0),
//                affineTransformationFactory.createZAxisRotationAffineTransformation(Math.PI / 4.0),
//                affineTransformationFactory.createYAxisRotationAffineTransformation(-Math.PI / 2.0)
//        );
        List<AffineTransformation> affineTransformations = Arrays.asList(
                affineTransformationFactory.createXAxisRotationAffineTransformation(Math.PI / 4.0),
                affineTransformationFactory.createXAxisRotationAffineTransformation(Math.PI / 4.0),
                affineTransformationFactory.createZAxisRotationAffineTransformation(-Math.PI / 4.0),
                affineTransformationFactory.createZAxisRotationAffineTransformation(-Math.PI / 4.0)
        );
        return affineTransformations;
    }
    
    private List<Quaternion> makeQuaternionsForInterpolationList() {
        return makeOrientationsForInterpolationList().stream()
                .map(Orientation::getRotation)
                .collect(Collectors.toList());
    }
    
    private List<Pair<Double, Double>> makePointsWithValuesForInterpolationList() {
        List<Pair<Double, Double>> pointsWithValues = Arrays.asList(
                new Pair<>(-7.0, 1.0),
                new Pair<>(-5.0, 1.0),
                new Pair<>(-3.0, 1.0),
                new Pair<>(-1.0, 3.0),
                new Pair<>(1.0, 1.0),
                new Pair<>(3.0, 1.0),
                new Pair<>(5.0, -3.0),
                new Pair<>(7.0, 0.0)
        );
        return pointsWithValues;
    }
    
}
