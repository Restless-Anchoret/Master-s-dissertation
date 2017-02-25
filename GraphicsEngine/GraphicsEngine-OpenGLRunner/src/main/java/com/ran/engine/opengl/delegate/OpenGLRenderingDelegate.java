package com.ran.engine.opengl.delegate;

import com.ran.engine.rendering.algebraic.vector.TwoIntVector;
import com.ran.engine.rendering.core.RenderingDelegate;
import org.lwjgl.opengl.Display;

import java.awt.*;

public class OpenGLRenderingDelegate implements RenderingDelegate {

    @Override
    public int getWidth() {
        return Display.getWidth();
    }

    @Override
    public int getHeight() {
        return Display.getHeight();
    }

    @Override
    public void setColor(Color color) {

    }

    @Override
    public void setPaintWidth(float paintWidth) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void drawLine(TwoIntVector firstPoint, TwoIntVector secondPoint) {

    }

    @Override
    public void drawCircle(TwoIntVector center, int radius) {

    }

    @Override
    public void drawRectangle(TwoIntVector firstPoint, TwoIntVector secondPoint) {

    }

}