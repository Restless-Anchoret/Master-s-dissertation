package com.ran.dissertation.labs.lab1;

import com.ran.dissertation.controller.MainController;
import com.ran.dissertation.factories.DefaultWorldFactory;
import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LabFirstMain {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | InstantiationException |
                    IllegalAccessException | UnsupportedLookAndFeelException exception) {
                exception.printStackTrace();
            }
            LabFirstMainController mainController = new LabFirstMainController();
            mainController.startApplication();
        });
    }
    
}