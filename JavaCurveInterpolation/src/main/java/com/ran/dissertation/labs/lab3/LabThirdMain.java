package com.ran.dissertation.labs.lab3;

import java.awt.EventQueue;

public class LabThirdMain {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LabThirdMainController mainController = new LabThirdMainController();
            mainController.startApplication();
        });
    }
    
}