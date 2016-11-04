package com.ran.dissertation.ui;

public interface ImagePanelListener {
    
    void mouseDraggedLeftMouseButton(ImagePanel imagePanel, int previousX, int previousY, int nextX, int nextY);
    void mouseDraggedMiddleMouseButton(ImagePanel imagePanel, int previousX, int previousY, int nextX, int nextY);
    void mouseDraggedRightMouseButton(ImagePanel imagePanel, int previousX, int previousY, int nextX, int nextY);
    void mouseWheelMoved(ImagePanel imagePanel, int notches);

}