package com.ran.dissertation.factories;

import com.ran.dissertation.world.Camera;
import com.ran.dissertation.world.DisplayableObject;
import com.ran.dissertation.world.Figure;
import com.ran.dissertation.world.World;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultWorldFactory implements WorldFactory {

    private static final DefaultWorldFactory INSTANCE = new DefaultWorldFactory();

    public static DefaultWorldFactory getInstance() {
        return INSTANCE;
    }
    
    private DefaultWorldFactory() { }
    
    public World makeWorldFromFigures(List<Figure> figures) {
        return new World(figures.stream()
                .map(figure -> new DisplayableObject(figure))
                .collect(Collectors.toList()),
                Collections.EMPTY_LIST,
                new Camera()
        );
    }

    @Override
    public World createWorld() {
        FigureFactory figureFactory = FigureFactory.getInstance();
        return makeWorldFromFigures(Arrays.asList(figureFactory.makePlainGrid(10, 10, 1.0, 1.0)));
    }
    
}