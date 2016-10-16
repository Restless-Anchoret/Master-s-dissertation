package com.ran.dissertation.oldcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.event.*;

public class DrawPanel extends JPanel {

    private static final int WIDTH = 800,
            HEIGHT = 600,
            POINT_RADIUS = 4;

    private static final Dimension STANDART = new Dimension(WIDTH, HEIGHT);
    private static final Color LINES_COLOR = Color.BLACK,
            BASIS_COLOR = Color.GRAY,
            POINT_COLOR = Color.RED;

    private static final double ANGLE_STEP = Math.PI / 288.0,
            MOVE_STEP = 0.05,
            ZOOM_STEP = 1.0;

    private static Figure basis = Figure.figureFromFile("values/basis.txt");

    private Figure figure;
    private Camera camera;
    private int currentVertice;
    private int currX, currY;
    private boolean smoothing = false;

    public DrawPanel(Figure figure, Camera camera) {
        setSize(STANDART);
        this.figure = figure;
        this.camera = camera;
        currentVertice = -1;

        addMouseMotionListener(new Mover());
        addMouseWheelListener(new Zoomer());
    }

    private class Mover implements MouseMotionListener {
        public void mouseDragged(MouseEvent event) {
            int nextX = event.getX();
            int nextY = event.getY();
            if ((event.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
                camera.moveX((nextX - currX) * MOVE_STEP);
                camera.moveY((nextY - currY) * MOVE_STEP);
            } else if ((event.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0) {
                camera.moveZ((nextY - currY) * MOVE_STEP);
            } else if ((event.getModifiersEx() & InputEvent.BUTTON2_DOWN_MASK) != 0) {
                camera.changeAngleXOY((currX - nextX) * ANGLE_STEP);
                camera.changeAngleZ((nextY - currY) * ANGLE_STEP);
            }
            currX = nextX;
            currY = nextY;
            repaint();
        }

        public void mouseMoved(MouseEvent event) {
            currX = event.getX();
            currY = event.getY();
        }
    }

    private class Zoomer implements MouseWheelListener {
        public void mouseWheelMoved(MouseWheelEvent event) {
            int notches = event.getWheelRotation();
            camera.zoom(notches * ZOOM_STEP);
            repaint();
        }
    }

    public Figure getFigure() {
        return figure;
    }

    public Camera getCamera() {
        return camera;
    }

    public Point3D getCurrentVertice() {
        if (currentVertice >= 0) {
            Matrix matrix = figure.getVertices();
            double x = matrix.get(0, currentVertice);
            double y = matrix.get(1, currentVertice);
            double z = matrix.get(2, currentVertice);
            return new Point3D(x, y, z);
        } else {
            return new Point3D(0.0, 0.0, 0.0);
        }
    }

    public void setCurrentVertice(int current) {
        currentVertice = current;
    }

    public boolean isSmoothing() {
        return smoothing;
    }

    public void setSmoothing(boolean smoothing) {
        this.smoothing = smoothing;
        repaint();
    }

    private static void drawPixel(Graphics g, int x, int y) {
        g.drawLine(x, y, x, y);
    }

    private void drawLine(Graphics2D g, Point2DInt first, Point2DInt second) {
        if (smoothing) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawLine(first.getX(), first.getY(), second.getX(), second.getY());
            return;
        }

        int xa = first.getX();
        int ya = first.getY();
        int xb = second.getX();
        int yb = second.getY();

        int deltaX = Math.abs(xb - xa);
        int deltaY = Math.abs(yb - ya);
        int signX = (xb - xa >= 0 ? 1 : -1);
        int signY = (yb - ya >= 0 ? 1 : -1);
        int x = xa;
        int y = ya;
        boolean flat = (deltaX >= deltaY);
        int error = (flat ? 2 * deltaY - deltaX : 2 * deltaX - deltaY);
        drawPixel(g, x, y);

        for (int i = 1; (flat ? i <= deltaX : i <= deltaY); i++) {
            if (flat) {
                if (error >= 0) {
                    y += signY;
                    error -= 2 * deltaX;
                }
                x += signX;
                error += 2 * deltaY;
            } else {
                if (error >= 0) {
                    x += signX;
                    error -= 2 * deltaY;
                }
                y += signY;
                error += 2 * deltaX;
            }
            drawPixel(g, x, y);
        }
    }

    @Override
    public void paintComponent(Graphics gr) {
        BufferedImage image = (BufferedImage) createImage(WIDTH + 1, HEIGHT + 1);
        Graphics2D g = (Graphics2D) image.getGraphics();

        Point2DInt[] basisVertices = basis.displayVerticesForCamera(camera);
        g.setColor(BASIS_COLOR);
        for (Figure.Edge edge : basis.getEdges()) {
            if (basisVertices[edge.first] != null && basisVertices[edge.second] != null) {
                drawLine(g, basisVertices[edge.first], basisVertices[edge.second]);
            }
        }

        Point2DInt[] figureVertices = figure.displayVerticesForCamera(camera);
        g.setColor(LINES_COLOR);
        for (Figure.Edge edge : figure.getEdges()) {
            if (figureVertices[edge.first] != null && figureVertices[edge.second] != null) {
                drawLine(g, figureVertices[edge.first], figureVertices[edge.second]);
            }
        }

        if (currentVertice >= 0) {
            int x = figureVertices[currentVertice].getX();
            int y = figureVertices[currentVertice].getY();
            g.setColor(POINT_COLOR);
            g.fillOval(x - POINT_RADIUS, y - POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2);
        }

        g.setColor(LINES_COLOR);
        g.drawRect(0, 0, WIDTH, HEIGHT);
        gr.drawImage(image, 0, 0, this);
    }

    @Override
    public Dimension getPreferredSize() {
        return STANDART;
    }

}
