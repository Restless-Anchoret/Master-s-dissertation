package com.ran.dissertation.ui;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ImagePanelListenerSupport {

    private Set<ImagePanelListener> imagePanelListeners = new HashSet<>();
    
    public void addImagePanelListener(ImagePanelListener listener) {
        imagePanelListeners.add(listener);
    }
    
    public Set<ImagePanelListener> getImagePanelListeners() {
        return Collections.unmodifiableSet(imagePanelListeners);
    }
    
    public void removeImagePanelListener(ImagePanelListener listener) {
        imagePanelListeners.remove(listener);
    }
    
    public void fireMouseMoved(ImagePanel imagePanel, int x, int y) {
        for (ImagePanelListener listener: imagePanelListeners) {
            listener.mouseMoved(imagePanel, x, y);
        }
    }
    
    public void fireMouseDraggedLeftMouseButton(ImagePanel imagePanel, int x, int y) {
        for (ImagePanelListener listener: imagePanelListeners) {
            listener.mouseDraggedLeftMouseButton(imagePanel, x, y);
        }
    }
    
    public void fireMouseDraggedMiddleMouseButton(ImagePanel imagePanel, int x, int y) {
        for (ImagePanelListener listener: imagePanelListeners) {
            listener.mouseDraggedMiddleMouseButton(imagePanel, x, y);
        }
    }
    
    public void fireMouseDraggedRightMouseButton(ImagePanel imagePanel, int x, int y) {
        for (ImagePanelListener listener: imagePanelListeners) {
            listener.mouseDraggedRightMouseButton(imagePanel, x, y);
        }
    }
    
    public void fireMouseWheelMoved(ImagePanel imagePanel, int notches) {
        for (ImagePanelListener listener: imagePanelListeners) {
            listener.mouseWheelMoved(imagePanel, notches);
        }
    }
    
}