package com.ran.dissertation.world;

import com.ran.dissertation.algebraic.exception.CreationException;
import com.ran.dissertation.algebraic.quaternion.Quaternion;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;

public class Orientation {

    public static final Orientation INITIAL_ORIENTATION =
            new Orientation(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, Quaternion.IDENTITY_QUANTERNION);
    
    public static Orientation createForOffset(ThreeDoubleVector offset) {
        return new Orientation(offset, Quaternion.IDENTITY_QUANTERNION);
    }
    
    public static Orientation createForOffset(double x, double y, double z) {
        return new Orientation(new ThreeDoubleVector(x, y, z), Quaternion.IDENTITY_QUANTERNION);
    }
    
    public static Orientation createForRotation(ThreeDoubleVector axis, double angle) {
        return createForOffsetAndRotation(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, axis, angle);
    }
    
    public static Orientation createForOffsetAndRotation(ThreeDoubleVector offset, ThreeDoubleVector axis, double angle) {
        return new Orientation(offset, new Quaternion(Math.cos(angle), axis.normalized().multiply(Math.sin(angle))));
    }
    
    public static Orientation createForOffsetAndRotation(double offsetX, double offsetY, double offsetZ,
            ThreeDoubleVector axis, double angle) {
        return new Orientation(new ThreeDoubleVector(offsetX, offsetY, offsetZ),
                new Quaternion(Math.cos(angle), axis.normalized().multiply(Math.sin(angle))));
    }
    
    private final ThreeDoubleVector offset;
    private final Quaternion rotation;
    private final Quaternion conjugateRotation;

    public Orientation(ThreeDoubleVector offset, Quaternion rotation) {
        if (!rotation.isIdentity()) {
            throw new CreationException("Quaternion which represents orientation must be identity");
        }
        this.offset = offset;
        this.rotation = rotation;
        this.conjugateRotation = rotation.getConjugate();
    }

    public ThreeDoubleVector getOffset() {
        return offset;
    }

    public Quaternion getRotation() {
        return rotation;
    }

    public Quaternion getConjugateRotation() {
        return conjugateRotation;
    }

    @Override
    public String toString() {
        return "Orientation{" + "offset=" + offset + ", rotation=" + rotation + '}';
    }
    
}