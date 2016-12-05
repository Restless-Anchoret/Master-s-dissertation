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
        Orientation sphereOrientation = Orientation.createForOffset(30.0, 0.0, 0.0);
        
        List<DisplayableObject> displayableObjects = Arrays.asList(
                
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
                        sphereOrientation, Color.RED, 0, 4)
        );
        List<AnimatedObject> animatedObjects = Arrays.asList(
//                new AnimatedObject(figureFactory.makeCube(2.0),
//                        animationFactory.makeZRotationAnimation(new ThreeDoubleVector(-6.0, -3.0, 2.0), 720)),
//                new AnimatedObject(figureFactory.makeCube(2.0),
//                        animationFactory.makeRotationAnimation(new ThreeDoubleVector(-6.0, 3.0, 2.0),
//                                new ThreeDoubleVector(1.0, 1.0, 1.0), 120)),
//                new AnimatedObject(figureFactory.makeCube(2.0 * Math.sqrt(3.0)),
//                        animationFactory.makeInterpolatedOrientationCurveAnimation(
//                                makeQuaternionsForInterpolationList(), 1, 1000,
//                                new ThreeDoubleVector(0.0, 0.0, 14.0)))
        );

        // Camera for plain curve interpolation presentation
//        Camera camera = new Camera(new ThreeDoubleVector(0.0, 6.0, 0.0), 0.25, 8.0);

        // Camera for sphere curve interpolation presentation
        Camera camera = Camera.createForPositionAndAngles(new ThreeDoubleVector(31.0, 8.0, 5.0),
                0.0, 0.7);//new Camera(new ThreeDoubleVector(0.0, 6.0, 0.0), 0.25, 8.0);
        
        // Camera for orientation curve interpolation presentation
//        Camera camera = new Camera(new ThreeDoubleVector(0.0, 6.0, 0.0), 0.25, 8.0);

        return new World(displayableObjects, animatedObjects, camera);
    }
    
    private List<ThreeDoubleVector> makeVerticesForInterpolationList() {
        return Arrays.asList(
                new ThreeDoubleVector(-5.0, 0.0, 0.0),
                new ThreeDoubleVector(0.0, 0.0, 5.0),
                new ThreeDoubleVector(0.0, 5.0, 0.0),
                new ThreeDoubleVector(5.0, 0.0, 0.0)
                        
//                new ThreeDoubleVector(0.0, 0.0, 3.0),
//                new ThreeDoubleVector(0.0, 3.0, 0.0),
//                new ThreeDoubleVector(3.0, 0.0, 0.0),
//                new ThreeDoubleVector(0.0, 0.0, -3.0),
//                new ThreeDoubleVector(0.0, -3.0, 0.0),
//                new ThreeDoubleVector(-3.0, 0.0, 0.0)
        );
//        return Arrays.asList(
//                getVectorForAngles(0.0, 0.0, 3.0),
//                getVectorForAngles(Math.PI * 0.125, 0.0, 3.0),
//                getVectorForAngles(Math.PI * 0.25, 0.0, 3.0),
//                getVectorForAngles(Math.PI * 0.5, Math.PI / 20.0, 3.0),
//                getVectorForAngles(Math.PI * 0.75, Math.PI / 20.0, 3.0),
//                getVectorForAngles(Math.PI * 1.0, Math.PI / 20.0, 3.0),
//                getVectorForAngles(Math.PI * 1.25, Math.PI / 20.0, 3.0),
//                getVectorForAngles(Math.PI * 1.5, 0.0, 3.0),
//                getVectorForAngles(Math.PI * 1.625, 0.0, 3.0),
//                getVectorForAngles(Math.PI * 1.75, 0.0, 3.0)
//        );
    }
    
    private ThreeDoubleVector getVectorForAngles(double angleXOY, double angleZ, double radius) {
        return new ThreeDoubleVector(Math.sin(angleXOY), Math.cos(angleXOY), Math.tan(angleZ)).normalized().multiply(radius);
    }
    
    private List<Quaternion> makeQuaternionsForInterpolationList() {
//        List<Orientation> orientations = Arrays.asList(
//                Orientation.INITIAL_ORIENTATION,
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI / 2.0),
//                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), Math.PI / 2.0),
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI),
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), -Math.PI / 2.0),
//                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), -Math.PI / 2.0)
//        );
        List<Orientation> orientations = Arrays.asList(
                Orientation.INITIAL_ORIENTATION,
                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI / 2.0),
                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI),
                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), Math.PI / 2.0),
                Orientation.INITIAL_ORIENTATION,
                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), -Math.PI / 2.0)//,
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), -Math.PI / 2.0)
        );
        return orientations.stream().map(
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
//                new Pair<>(-2.0, 2.0),
//                new Pair<>(0.0, 4.0),
//                new Pair<>(2.0, 6.0),
//                new Pair<>(5.0, 3.0),
//                new Pair<>(10.0, 2.0),
//                new Pair<>(14.0, 8.0),
//                new Pair<>(15.0, 1.0)
        );
        return pointsWithValues;
    }
    
}