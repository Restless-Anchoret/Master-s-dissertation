package com.ran.dissertation.world;

import com.ran.dissertation.algebraic.quaternion.Quaternion;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import java.util.List;
import java.util.stream.Collectors;

public class OrientationMapper {

    private static final OrientationMapper INSTANCE = new OrientationMapper();
    
    public static OrientationMapper getInstance() {
        return INSTANCE;
    }
    
    private OrientationMapper() { }
    
    public List<ThreeDoubleVector> orientVertices(List<ThreeDoubleVector> initialVertices, Orientation orientation) {
        return initialVertices.stream().sequential()
                .map(vertice -> orientation.getRotation()
                    .multiply(Quaternion.createFromVector(vertice))
                    .multiply(orientation.getConjugateRotation())
                    .getVector().add(orientation.getOffset())
        ).collect(Collectors.toList());
    }
    
}