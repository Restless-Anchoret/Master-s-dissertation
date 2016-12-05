package com.ran.dissertation.main;

import com.ran.dissertation.controller.MainController;
import com.ran.dissertation.factories.InterpolationPresentationWorldFactory;
import java.awt.EventQueue;

public class InterpolationPresentationMain {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainController controller = new MainController(new InterpolationPresentationWorldFactory());
            controller.startApplication();
        });
    }
    
}