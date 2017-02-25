package com.ran.engine.rendering.core;

import com.ran.engine.rendering.algebraic.vector.TwoIntVector;

import java.awt.*;

/**
 * Created by RestlessAnchoret on 25.02.2017.
 */
public interface RenderingDelegate {

    int getWidth();
    int getHeight();

    void setColor(Color color);
    void setPaintWidth(float paintWidth);

    void clear();
    void drawLine(TwoIntVector firstPoint, TwoIntVector secondPoint);
    void drawCircle(TwoIntVector center, int radius);
    void drawRectangle(TwoIntVector firstPoint, TwoIntVector secondPoint);

    default void clear(Color color) {
        setColor(color);
        clear();
    }

    default void drawLine(TwoIntVector firstPoint, TwoIntVector secondPoint, Color color) {
        setColor(color);
        drawLine(firstPoint, secondPoint);
    }

    default void drawLine(TwoIntVector firstPoint, TwoIntVector secondPoint, Color color, float paintWidth) {
        setColor(color);
        setPaintWidth(paintWidth);
        drawLine(firstPoint, secondPoint, color, paintWidth);
    }

    default void drawCircle(TwoIntVector center, Color color, int radius) {
        setColor(color);
        drawCircle(center, radius);
    }

    default void drawRectangle(TwoIntVector firstPoint, TwoIntVector secondPoint, Color color) {
        setColor(color);
        drawRectangle(firstPoint, secondPoint);
    }

}