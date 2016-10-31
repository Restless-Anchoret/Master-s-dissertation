package com.ran.dissertation.factories;

import com.ran.dissertation.world.DisplayableObject;
import com.ran.dissertation.world.Figure;
import com.ran.dissertation.world.World;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WorldFactory {

    private static final WorldFactory INSTANCE = new WorldFactory();

    public static WorldFactory getInstance() {
        return INSTANCE;
    }
    
    private WorldFactory() { }
    
    public World makeWorldFromFigures(List<Figure> figures) {
        return new World(figures.stream()
                .map(figure -> new DisplayableObject(figure))
                .collect(Collectors.toList()),
                Collections.EMPTY_LIST);
    }
    
    public World makeSampleWorld() {
        FigureFactory figureFactory = FigureFactory.getInstance();
        return makeWorldFromFigures(Arrays.asList(figureFactory.makePlainGrid(10, 10, 1.0, 1.0)));
    }
    
}