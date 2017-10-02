package com.ran.engine.factories.world;

import com.ran.engine.algebra.vector.ThreeDoubleVector;
import com.ran.engine.rendering.world.Camera;
import com.ran.engine.rendering.world.Orientation;
import com.ran.engine.rendering.world.WorldObjectCreator;
import com.ran.engine.rendering.world.WorldObjectPartBuilder;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class BezierWorldFactory extends BaseWorldFactory {

    @Override
    protected Camera getCamera() {
        return Camera.createForPositionAndAngles(new ThreeDoubleVector(-3.0, 4.0, 6.0), 0.5, 0.35);
    }

    @Override
    protected List<WorldObjectCreator> getWorldObjectCreators() {
        List<ThreeDoubleVector> sphereCurveVertices = makeVerticesForInterpolationList();
        Orientation sphereOrientation = Orientation.createForOffset(-6.0, 0.0, 4.0);

        return Arrays.asList(
                plainGridCreator(20, 16, 1.0, 1.0,
                        Orientation.INITIAL_ORIENTATION),
                fixedObjectCreator(Arrays.asList(
                        createStandardGlobe(3.0),
                        new WorldObjectPartBuilder()
                                .setFigure(getDemonstrationFiguresFactory().makeFigureByBigArcs(sphereCurveVertices, 20))
                                .setColor(Color.BLUE).setEdgePaintWidth(1.5f).setVerticePaintRadius(2).build(),
                        new WorldObjectPartBuilder()
                                .setFigure(getInterpolatedFiguresFactory().makeBezierCurveByMiddlePoints(sphereCurveVertices, 1, 100))
                                .setEdgePaintWidth(1.5f).setVerticePaintRadius(2).build(),
                        new WorldObjectPartBuilder()
                                .setFigure(getFigureFactory().makeFigureByPoints(sphereCurveVertices))
                                .setColor(Color.RED).setVerticePaintRadius(7).build()),
                        sphereOrientation));
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
    }

}
