package com.ran.engine.factories.constants;

import com.ran.engine.algebra.common.Pair;
import com.ran.engine.algebra.quaternion.Quaternion;
import com.ran.engine.factories.animations.AffineTransformationFactory;
import com.ran.engine.rendering.world.AffineTransformation;
import com.ran.engine.rendering.world.Orientation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuaternionsConstants {

    private QuaternionsConstants() { }

    public static List<AffineTransformation> makeAffineTransformationsList() {
        AffineTransformationFactory affineTransformationFactory = new AffineTransformationFactory();
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
        return Arrays.asList(
                affineTransformationFactory.createXAxisRotationAffineTransformation(Math.PI / 4.0),
                affineTransformationFactory.createXAxisRotationAffineTransformation(Math.PI / 4.0),
                affineTransformationFactory.createZAxisRotationAffineTransformation(-Math.PI / 4.0),
                affineTransformationFactory.createZAxisRotationAffineTransformation(-Math.PI / 4.0)
        );
    }

    public static List<Quaternion> makeQuaternionsForInterpolationList(
            List<AffineTransformation> affineTransformations) {
        List<Orientation> orientations = new ArrayList<>();
        orientations.add(Orientation.INITIAL_ORIENTATION);
        for (AffineTransformation affineTransformation: affineTransformations) {
            Orientation lastOrientation = orientations.get(orientations.size() - 1);
            orientations.add(affineTransformation.apply(lastOrientation));
        }
        return orientations.stream()
                .map(Orientation::getRotation)
                .collect(Collectors.toList());
    }

    public static List<Double> makeTangentAnglesList() {
        return Arrays.asList(
                null,
                -Math.PI * 0.75,
                -Math.PI * 0.5,
                Math.PI * 0.75,
                null
        );
    }

    public static List<Pair<Quaternion, Double>> zipQuaternionsAndAnglesLists(List<Quaternion> quaternions,
                                                                              List<Double> angles) {
        int resultListSize = Math.min(quaternions.size(), angles.size());
        List<Pair<Quaternion, Double>> resultList = new ArrayList<>(resultListSize);
        for (int i = 0; i < resultListSize; i++) {
            resultList.add(new Pair<>(quaternions.get(i), angles.get(i)));
        }
        return resultList;
    }

}
