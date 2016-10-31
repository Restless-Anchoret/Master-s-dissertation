package com.ran.dissertation.world;

import com.ran.dissertation.algebraic.exception.CreationException;
import com.ran.dissertation.algebraic.quaternion.Quaternion;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;

public class Orientation {

    public static final Orientation INITIAL_ORIENTATION =
            new Orientation(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, Quaternion.IDENTITY_QUANTERNION);
    
    public static final Orientation createForOffset(ThreeDoubleVector offset) {
        return new Orientation(offset, Quaternion.IDENTITY_QUANTERNION);
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