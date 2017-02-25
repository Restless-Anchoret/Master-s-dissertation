package com.ran.engine.opengl.delegate;

import com.ran.engine.rendering.algebraic.vector.TwoIntVector;
import com.ran.engine.rendering.core.RenderingDelegate;
import org.lwjgl.opengl.*;

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
        GL11.glColor3f(
                convertIntColorValueToFloat(color.getRed()),
                convertIntColorValueToFloat(color.getGreen()),
                convertIntColorValueToFloat(color.getBlue())
        );
    }

    private float convertIntColorValueToFloat(int value) {
        return ((float)value) / 256;
    }

    @Override
    public void setPaintWidth(float paintWidth) {
        GL11.glLineWidth(paintWidth);
    }

    @Override
    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        drawRectangle(TwoIntVector.ZERO_TWO_INT_VECTOR, new TwoIntVector(getWidth(), getHeight()));
    }

    @Override
    public void drawLine(TwoIntVector firstPoint, TwoIntVector secondPoint) {
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2f(firstPoint.getX(), firstPoint.getY());
        GL11.glVertex2f(secondPoint.getX(), secondPoint.getY());
        GL11.glEnd();
    }

    @Override
    public void drawCircle(TwoIntVector center, int radius) {
        // todo: replace this code by something simplier
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex2f(center.getX(), center.getY());
        for (float angle = 0.0f; angle < 361.0f; angle += 45.f) {
            float x = center.getX() + (float)Math.sin(angle) * radius;
            float y = center.getY() + (float)Math.cos(angle) * radius;
            GL11.glVertex2f(x, y);
        }
        GL11.glEnd();
    }

    @Override
    public void drawRectangle(TwoIntVector firstPoint, TwoIntVector secondPoint) {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(firstPoint.getX(),firstPoint.getY());
        GL11.glVertex2f(secondPoint.getX(),firstPoint.getY());
        GL11.glVertex2f(secondPoint.getX(),secondPoint.getY());
        GL11.glVertex2f(firstPoint.getX(),secondPoint.getY());
        GL11.glEnd();
    }

}