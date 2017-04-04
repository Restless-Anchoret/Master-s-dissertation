package com.ran.engine.factories.world;

import com.ran.engine.factories.objects.*;
import com.ran.engine.factories.util.CoordinatesConverter;
import com.ran.engine.rendering.algebraic.common.Pair;
import com.ran.engine.rendering.algebraic.quaternion.Quaternion;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;
import com.ran.engine.rendering.world.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InterpolationPresentationWorldFactory implements WorldFactory {

    private static final InterpolationPresentationWorldFactory INSTANCE = new InterpolationPresentationWorldFactory();

    public static InterpolationPresentationWorldFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public World createWorld() {
        FigureFactory figureFactory = FigureFactory.getInstance();
        InterpolatedFiguresFactory interpolatedFiguresFactory = InterpolatedFiguresFactory.getInstance();
        DemonstrationFiguresFactory demonstrationFiguresFactory = DemonstrationFiguresFactory.getInstance();
        AnimationFactory animationFactory = AnimationFactory.getInstance();
        
        List<Pair<Double, Double>> pointsWithValues = makePointsWithValuesForInterpolationList();
        List<ThreeDoubleVector> sphereCurveVertices = makeVerticesForInterpolationList();
        List<Quaternion> quaternions = makeQuaternionsForInterpolationList();
        
        Orientation sphereOrientation = Orientation.createForOffset(30.0, 0.0, 0.0);
        Orientation cubeOrientation = Orientation.createForOffset(60.0, 0.0, 0.0);
        Figure figureForRotation = figureFactory.makeGrid(1, 1, 1, 2.0, 2.0, 2.0,
                new ThreeDoubleVector(0.0, 0.0, 4.0));
        
        List<DisplayableObject> displayableObjects = new ArrayList<>();
        displayableObjects.addAll(Arrays.asList(
                // Plain curve interpolation
                new StaticObject(figureFactory.makeGrid(20, 0, 16, 1.0, 0.0, 1.0),
                        Orientation.INITIAL_ORIENTATION,
                        Color.LIGHT_GRAY),
                new StaticObject(figureFactory.makeGrid(20, 0, 0, 1.0, 0.0, 0.0)),
                new StaticObject(figureFactory.makeGrid(0, 0, 16, 0.0, 0.0, 1.0)),
                new StaticObject(demonstrationFiguresFactory.makeFigureByParabolas(pointsWithValues, 20,
                        CoordinatesConverter.CONVERTER_TO_XZ),
                        Orientation.INITIAL_ORIENTATION, Color.BLUE, 1.5f, 2),
                new StaticObject(interpolatedFiguresFactory.makeSpline(pointsWithValues, 1, 140,
                        CoordinatesConverter.CONVERTER_TO_XZ),
                        Orientation.INITIAL_ORIENTATION, Color.BLACK, 1.5f, 2),
                new StaticObject(figureFactory.makeFigureByPoints(pointsWithValues,
                        CoordinatesConverter.CONVERTER_TO_XZ),
                        Orientation.INITIAL_ORIENTATION, Color.RED, 0, 4),
                
                // Sphere curve interpolation
                new StaticObject(figureFactory.makeGlobe(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, 5.0, 12),
                        sphereOrientation, Color.LIGHT_GRAY, 1, 0),
                new StaticObject(demonstrationFiguresFactory.makeFigureByArcs(sphereCurveVertices, 20),
                        sphereOrientation, Color.BLUE, 1.5f, 2),
                new StaticObject(interpolatedFiguresFactory.makeInterpolatedCurve(sphereCurveVertices, 1, 100),
                        sphereOrientation, Color.BLACK, 1.5f, 2),
                new StaticObject(new Figure(sphereCurveVertices, Collections.EMPTY_LIST),
                        sphereOrientation, Color.RED, 0, 4),
                
                // Orientation curve interpolation
                new StaticObject(figureFactory.makeCube(10.0),
                        cubeOrientation, Color.LIGHT_GRAY)
        ));
        
        for (Quaternion quaternion: quaternions) {
            displayableObjects.add(new StaticObject(figureForRotation,
                    new Orientation(cubeOrientation.getOffset(), quaternion), Color.GRAY));
        }

        displayableObjects.add(
                new DisplayableObjectBuilder(figureForRotation,
                        animationFactory.makeInterpolatedOrientationCurveAnimation(
                        quaternions, 2, 30))
                        .setOffset(cubeOrientation.getOffset())
                        .setEdgePaintWidth(2.0f)
                        .setVerticePaintRadius(2)
                        .setAnimationCyclic(false).build()
        );

        // Camera for plain curve interpolation presentation
//        Camera camera = new Camera(new ThreeDoubleVector(0.0, 6.0, 0.0), 0.25, 8.0);

        // Camera for sphere curve interpolation presentation
//        Camera camera = Camera.createForPositionAndAngles(new ThreeDoubleVector(31.0, 7.0, 5.5), 0.0, 0.7);
        
        // Camera for orientation curve interpolation presentation
        Camera camera = Camera.createForPositionAndAngles(new ThreeDoubleVector(54.5, 10.0, 2.5), -0.5, 0.3);

        return new World(displayableObjects, camera);
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
        AffineTransformationFactory affineTransformationFactory = AffineTransformationFactory.getInstance();
        List<AffineTransformation> affineTransformations = Arrays.asList(
                affineTransformationFactory.createXAxisRotationAffineTransformation(Math.PI / 4.0),
                affineTransformationFactory.createXAxisRotationAffineTransformation(Math.PI / 4.0),
                affineTransformationFactory.createZAxisRotationAffineTransformation(Math.PI / 2.0),
                affineTransformationFactory.createYAxisRotationAffineTransformation(-Math.PI / 2.0),
                affineTransformationFactory.createXAxisRotationAffineTransformation(Math.PI / 4.0),
                affineTransformationFactory.createXAxisRotationAffineTransformation(Math.PI / 4.0),
                affineTransformationFactory.createZAxisRotationAffineTransformation(Math.PI / 4.0),
                affineTransformationFactory.createZAxisRotationAffineTransformation(Math.PI / 4.0),
                affineTransformationFactory.createYAxisRotationAffineTransformation(-Math.PI / 2.0)
        );
        return affineTransformations;
    }
    
    private List<Quaternion> makeQuaternionsForInterpolationList() {
        return makeOrientationsForInterpolationList().stream().map(
                orientation -> orientation.getRotation()
        ).collect(Collectors.toList());
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