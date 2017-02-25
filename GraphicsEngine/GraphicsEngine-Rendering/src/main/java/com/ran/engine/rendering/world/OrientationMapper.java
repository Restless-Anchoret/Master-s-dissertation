package com.ran.engine.rendering.world;

import com.ran.engine.rendering.algebraic.quaternion.Quaternion;
import com.ran.engine.rendering.algebraic.vector.ThreeDoubleVector;
import java.util.List;
import java.util.stream.Collectors;

public class OrientationMapper {

    private static final OrientationMapper INSTANCE = new OrientationMapper();
    
    public static OrientationMapper getInstance() {
        return INSTANCE;
    }
    
    private OrientationMapper() { }
    
    public ThreeDoubleVector orientVertice(ThreeDoubleVector initialVertice, Orientation orientation) {
        return orientation.getRotation()
                .multiply(Quaternion.createFromVector(initialVertice
                        .elementWiseMultiply(orientation.getScaleReflectionVector())))
                .multiply(orientation.getConjugateRotation())
                .getVector().add(orientation.getOffset());
    }
    
    public List<ThreeDoubleVector> orientVertices(List<ThreeDoubleVector> initialVertices, Orientation orientation) {
        return initialVertices.stream().sequential()
                .map(vertice -> orientVertice(vertice, orientation))
                .collect(Collectors.toList());
    }
    
}