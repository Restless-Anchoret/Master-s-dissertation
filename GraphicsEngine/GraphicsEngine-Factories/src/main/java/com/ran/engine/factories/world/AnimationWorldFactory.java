package com.ran.engine.factories.world;

import com.ran.engine.algebra.quaternion.Quaternion;
import com.ran.engine.factories.constants.QuaternionsConstants;
import com.ran.engine.rendering.world.Camera;
import com.ran.engine.rendering.world.Orientation;
import com.ran.engine.rendering.world.WorldObjectCreator;

import java.util.ArrayList;
import java.util.List;

public class AnimationWorldFactory extends BaseWorldFactory {

    @Override
    protected List<WorldObjectCreator> getWorldObjectCreators() {
        Orientation simpleInterpolationOrientation = Orientation.createForOffset(-30.0, 0.0, 0.0);
        Orientation bezierInterpolationOrientation = Orientation.INITIAL_ORIENTATION;
        Orientation tangentInterpolationOrientation = Orientation.createForOffset(30.0, 0.0, 0.0);

        List<Quaternion> quaternions = QuaternionsConstants.makeQuaternionsForInterpolationList(
                QuaternionsConstants.makeAffineTransformationsList());

        List<WorldObjectCreator> worldObjectCreators = new ArrayList<>();

        // Orientation interpolation by points
        worldObjectCreators.addAll(animationPresentationObjectCreators(
                simpleInterpolationOrientation, quaternions, 100,
                () -> getAnimationFactory().makeInterpolatedOrientationCurveAnimation(quaternions, 2, 15)));

        // Orientation Bezier interpolation
        worldObjectCreators.addAll(animationPresentationObjectCreators(
                bezierInterpolationOrientation, quaternions, 100,
                () -> getAnimationFactory().makeInterpolatedOrientationBezierCurveAnimation(quaternions, 2, 15)));

        // Orientation interpolation by points with tangent angles
        // todo

        return worldObjectCreators;
    }

    @Override
    protected Camera getCamera() {
        return new Camera();
    }

}
