package com.ran.dissertation.factories;

import com.ran.dissertation.algebraic.common.Pair;
import com.ran.dissertation.algebraic.quaternion.Quaternion;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.world.AnimatedObject;
import com.ran.dissertation.world.Camera;
import com.ran.dissertation.world.DisplayableObject;
import com.ran.dissertation.world.Figure;
import com.ran.dissertation.world.Orientation;
import com.ran.dissertation.world.World;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InterpolationPresentationWorldFactory implements WorldFactory {

    @Override
    public World createWorld() {
        FigureFactory figureFactory = FigureFactory.getInstance();
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
                new DisplayableObject(figureFactory.makeGrid(20, 0, 16, 1.0, 0.0, 1.0),
                        Orientation.INITIAL_ORIENTATION,
                        Color.LIGHT_GRAY),
                new DisplayableObject(figureFactory.makeGrid(20, 0, 0, 1.0, 0.0, 0.0)),
                new DisplayableObject(figureFactory.makeGrid(0, 0, 16, 0.0, 0.0, 1.0)),
                new DisplayableObject(figureFactory.makeFigureByParabolas(pointsWithValues, 20,
                        CoordinatesConverter.CONVERTER_TO_XZ),
                        Orientation.INITIAL_ORIENTATION, Color.BLUE, 1.5f, 2),
                new DisplayableObject(figureFactory.makeSpline(pointsWithValues, 1, 140,
                        CoordinatesConverter.CONVERTER_TO_XZ),
                        Orientation.INITIAL_ORIENTATION, Color.BLACK, 1.5f, 2),
                new DisplayableObject(figureFactory.makeFigureByPoints(pointsWithValues,
                        CoordinatesConverter.CONVERTER_TO_XZ),
                        Orientation.INITIAL_ORIENTATION, Color.RED, 0, 4),
                
                // Sphere curve interpolation
                new DisplayableObject(figureFactory.makeGlobe(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, 5.0, 12),
                        sphereOrientation, Color.LIGHT_GRAY, 1, 0),
                new DisplayableObject(figureFactory.makeFigureByArcs(sphereCurveVertices, 20),
                        sphereOrientation, Color.BLUE, 1.5f, 2),
                new DisplayableObject(figureFactory.makeInterpolatedCurve(sphereCurveVertices, 1, 100),
                        sphereOrientation, Color.BLACK, 1.5f, 2),
                new DisplayableObject(new Figure(sphereCurveVertices, Collections.EMPTY_LIST),
                        sphereOrientation, Color.RED, 0, 4),
                
                // Orientation curve interpolation
                new DisplayableObject(//figureFactory.makeGrid(2, 2, 2, 5.0, 5.0, 5.0),
                        figureFactory.makeCube(10.0),
                        cubeOrientation, Color.LIGHT_GRAY)
        ));
        
        for (Quaternion quaternion: quaternions) {
            displayableObjects.add(new DisplayableObject(figureForRotation,
                    new Orientation(cubeOrientation.getOffset(), quaternion), Color.GRAY));
        }
        
        List<AnimatedObject> animatedObjects = Arrays.asList(
                new AnimatedObject(figureForRotation, animationFactory.makeInterpolatedOrientationCurveAnimation(
                        quaternions, 2, 800, new ThreeDoubleVector(60.0, 0.0, 0.0)),
                        Color.BLACK, 2.0f, 2)
        );

        // Camera for plain curve interpolation presentation
//        Camera camera = new Camera(new ThreeDoubleVector(0.0, 6.0, 0.0), 0.25, 8.0);

        // Camera for sphere curve interpolation presentation
//        Camera camera = Camera.createForPositionAndAngles(new ThreeDoubleVector(31.0, 8.0, 5.0),
//                0.0, 0.7);
        
        // Camera for orientation curve interpolation presentation
        Camera camera = Camera.createForPositionAndAngles(new ThreeDoubleVector(55.0, 8.0, 2.0), -0.5, 0.3);

        return new World(displayableObjects, animatedObjects, camera);
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
    
    private List<Orientation> makeOrientationsForInterpolationList() {
        List<Orientation> orientations = Arrays.asList(
                Orientation.INITIAL_ORIENTATION,
                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI / 2.0),
                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), Math.PI / 2.0),
                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), Math.PI),
                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), -Math.PI / 2.0),
                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), -Math.PI / 2.0),
                Orientation.INITIAL_ORIENTATION
        );
        // todo: some bug with these orientations - need to check
//        List<Orientation> orientations = Arrays.asList(
//                Orientation.INITIAL_ORIENTATION,
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI / 2.0),
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI),
//                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), Math.PI / 2.0),
//                Orientation.INITIAL_ORIENTATION,
//                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), -Math.PI / 2.0),
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), -Math.PI / 2.0)
//        );
        return orientations;
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