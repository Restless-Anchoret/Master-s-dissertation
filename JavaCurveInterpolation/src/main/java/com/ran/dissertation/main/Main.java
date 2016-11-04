package com.ran.dissertation.main;

import com.ran.dissertation.controller.MainController;
import com.ran.dissertation.factories.DefaultWorldFactory;

public class Main {

    public static void main(String[] args) {
        MainController mainController = new MainController(DefaultWorldFactory.getInstance());
        mainController.startApplication();
    }
    
}