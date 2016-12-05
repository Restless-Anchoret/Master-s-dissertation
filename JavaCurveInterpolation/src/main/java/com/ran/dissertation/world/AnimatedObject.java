package com.ran.dissertation.world;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class AnimatedObject extends DisplayableObject {
    
    private List<Orientation> orientations;
    private int currentOrientationIndex = 0;
    
    public AnimatedObject(Figure figure, List<Orientation> orientations, Color color, float edgePaintWidth, int verticePaintRadius) {
        super(figure, (orientations.isEmpty() ? Orientation.INITIAL_ORIENTATION : orientations.get(0)),
                color, edgePaintWidth, verticePaintRadius);
        this.orientations = (orientations.isEmpty() ? Collections.singletonList(Orientation.INITIAL_ORIENTATION) : orientations);
    }
    
    public AnimatedObject(Figure figure, List<Orientation> orientations, Color color) {
        this(figure, orientations, color, DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }
    
    public AnimatedObject(Figure figure, List<Orientation> orientations) {
        this(figure, orientations, DEFAULT_FIGURE_COLOR, DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }

    public List<Orientation> getOrientations() {
        return Collections.unmodifiableList(orientations);
    }

    public int getCurrentOrientationIndex() {
        return currentOrientationIndex;
    }
    
    public void goToNextOrientation() {
        currentOrientationIndex = (currentOrientationIndex + 1) % orientations.size();
        setOrientation(orientations.get(currentOrientationIndex));
    }
    
}