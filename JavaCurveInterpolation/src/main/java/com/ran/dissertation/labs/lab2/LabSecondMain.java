package com.ran.dissertation.labs.lab2;

import java.awt.EventQueue;

public class LabSecondMain {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LabSecondMainController mainController = new LabSecondMainController();
            mainController.startApplication();
        });
    }
    
}