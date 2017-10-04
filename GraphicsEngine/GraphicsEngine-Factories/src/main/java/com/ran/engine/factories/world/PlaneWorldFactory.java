package com.ran.engine.factories.world;

import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.vector.ThreeDoubleVector;
import com.ran.engine.algebra.vector.TwoDoubleVector;
import com.ran.engine.factories.constants.PlanePointsConstants;
import com.ran.engine.factories.util.CoordinatesConverter;
import com.ran.engine.rendering.world.Camera;
import com.ran.engine.rendering.world.Orientation;
import com.ran.engine.rendering.world.WorldObjectCreator;
import com.ran.engine.rendering.world.WorldObjectPartBuilder;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class PlaneWorldFactory extends BaseWorldFactory {

    @Override
    protected Camera getCamera() {
        return Camera.createForPositionAndAngles(new ThreeDoubleVector(0.0, 10.0, 0.0), 0.0, 0.0);
    }

    @Override
    protected List<WorldObjectCreator> getWorldObjectCreators() {
        Orientation simpleInterpolationOrientation = Orientation.createForOffset(-30.0, 0.0, 0.0);
        Orientation bezierInterpolationOrientation = Orientation.INITIAL_ORIENTATION;
        Orientation tangentInterpolationOrientation = Orientation.createForOffset(30.0, 0.0, 0.0);

        List<TwoDoubleVector> simpleInterpolationVertices = PlanePointsConstants.getListForSimpleInterpolation();
        List<Pair<TwoDoubleVector, Double>> tangentInterpolationVertices = PlanePointsConstants.getListForTangentInterpolation();

        return Arrays.asList(
                // Interpolation by points
                plainGridCreator(20, 16, 1.0, 1.0,
                        simpleInterpolationOrientation, true),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(getDemonstrationFiguresFactory()
                                .makeFigureByCircleArcs(simpleInterpolationVertices, 20))
                                .setColor(Color.BLUE).build(),
                        simpleInterpolationOrientation),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(getInterpolatedFiguresFactory()
                                .makePlaneInterpolatedCurveByPoints(simpleInterpolationVertices, 2, 200)).build(),
                        simpleInterpolationOrientation),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(getFigureFactory()
                                .makeFigureByPoints(simpleInterpolationVertices, CoordinatesConverter.CONVERTER_TO_XZ))
                                .setColor(Color.RED).setEdgePaintWidth(0f).setVerticePaintRadius(7).build(),
                        simpleInterpolationOrientation),

                // Bezier interpolation
                plainGridCreator(20, 16, 1.0, 1.0,
                        bezierInterpolationOrientation, true),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(getDemonstrationFiguresFactory()
                                .makeFigureBySegments(simpleInterpolationVertices, 20))
                                .setColor(Color.BLUE).build(),
                        bezierInterpolationOrientation),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(getInterpolatedFiguresFactory()
                                .makePlaneBezierCurveByPoints(simpleInterpolationVertices, 2, 200)).build(),
                        bezierInterpolationOrientation),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(getFigureFactory()
                                .makeFigureByPoints(simpleInterpolationVertices, CoordinatesConverter.CONVERTER_TO_XZ))
                                .setColor(Color.RED).setEdgePaintWidth(0f).setVerticePaintRadius(7).build(),
                        bezierInterpolationOrientation),

                // Interpolation by tangent angles
                plainGridCreator(20, 16, 1.0, 1.0,
                        tangentInterpolationOrientation, true)
                // todo: add some objects
        );
    }

}
