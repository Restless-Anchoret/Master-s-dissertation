package com.ran.dissertation.main;

import com.ran.dissertation.controller.MainController;
import com.ran.dissertation.factories.DefaultWorldFactory;
import com.ran.dissertation.factories.InterpolationPresentationWorldFactory;
import java.awt.EventQueue;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainController mainController = new MainController(Arrays.asList(
                    DefaultWorldFactory.getInstance(),
                    new InterpolationPresentationWorldFactory()
            ));
            mainController.startApplication();
        });
    }

}