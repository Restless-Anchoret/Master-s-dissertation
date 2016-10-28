package com.ran.dissertation.ui;

public interface ImagePanelListener {
    
    void mouseMoved(ImagePanel imagePanel, int x, int y);
    void mouseDraggedLeftMouseButton(ImagePanel imagePanel, int x, int y);
    void mouseDraggedMiddleMouseButton(ImagePanel imagePanel, int x, int y);
    void mouseDraggedRightMouseButton(ImagePanel imagePanel, int x, int y);
    void mouseWheelMoved(ImagePanel imagePanel, int notches);

}