package com.ran.engine.factories.world;

import com.ran.engine.factories.objects.AnimationFactory;
import com.ran.engine.factories.objects.FigureFactory;
import com.ran.engine.rendering.algebraic.function.DoubleFunction;
import com.ran.engine.rendering.algebraic.quaternion.Quaternion;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;
import com.ran.engine.rendering.world.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultWorldFactory implements WorldFactory {

    private static final DefaultWorldFactory INSTANCE = new DefaultWorldFactory();

    public static DefaultWorldFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public World createWorld() {
        FigureFactory figureFactory = FigureFactory.getInstance();
        AnimationFactory animationFactory = AnimationFactory.getInstance();
        DoubleFunction<Quaternion> animation = animationFactory.makeInterpolatedOrientationCurveAnimation(
                makeQuaternionsForInterpolationList(), 2, 30);
        List<DisplayableObject> displayableObjects = Arrays.asList(
                new StaticObject(figureFactory.makePlainGrid(20, 8, 1.0, 1.0),
                        Orientation.INITIAL_ORIENTATION,
                        Color.LIGHT_GRAY),
                new StaticObject(figureFactory.makeGlobe(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, 3.0, 12),
                        Orientation.createForOffset(-6.0, 0.0, 4.0),
                        Color.LIGHT_GRAY, 1, 0),
                new StaticObject(figureFactory.makeGlobe(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, 3.0, 12),
                        Orientation.createForOffset(6.0, 0.0, 4.0),
                        Color.LIGHT_GRAY, 1, 0),
                new DisplayableObjectBuilder(figureFactory.makeInterpolatedCurve(
                        makeVerticesForInterpolationList(), 1, 100), animation)
                        .setOffset(new ThreeDoubleVector(-6.0, 0.0, 4.0)).build(),
                new DisplayableObjectBuilder(figureFactory.makeCube(2.0 * Math.sqrt(3.0)), animation)
                        .setOffset(new ThreeDoubleVector(6.0, 0.0, 4.0)).build()
        );
        Camera camera = Camera.createForPositionAndAngles(new ThreeDoubleVector(0.0, 10.0, 4.0), 0.0, 0.0);
        return new World(displayableObjects, camera);
    }
    
    private List<ThreeDoubleVector> makeVerticesForInterpolationList() {
        return Arrays.asList(
                new ThreeDoubleVector(0.0, 0.0, 3.0),
                new ThreeDoubleVector(0.0, 3.0, 0.0),
                new ThreeDoubleVector(3.0, 0.0, 0.0),
                new ThreeDoubleVector(0.0, 0.0, -3.0),
                new ThreeDoubleVector(0.0, -3.0, 0.0),
                new ThreeDoubleVector(-3.0, 0.0, 0.0)
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
        // todo: replace this list by list of Quaternions
        List<Orientation> orientations = Arrays.asList(
                Orientation.INITIAL_ORIENTATION,
                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI / 2.0),
                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), Math.PI / 2.0),
                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI),
                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), -Math.PI / 2.0),
                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), -Math.PI / 2.0)
        );
//        List<Orientation> orientations = Arrays.asList(
//                Orientation.INITIAL_ORIENTATION,
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI / 2.0),
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), Math.PI),
//                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), Math.PI / 2.0),
//                Orientation.INITIAL_ORIENTATION,
//                Orientation.createForRotation(new ThreeDoubleVector(0.0, 1.0, 0.0), -Math.PI / 2.0)//,
//                Orientation.createForRotation(new ThreeDoubleVector(1.0, 0.0, 0.0), -Math.PI / 2.0)
//        );
        return orientations.stream().map(
                orientation -> orientation.getRotation()
        ).collect(Collectors.toList());
    }
    
}