package com.ran.dissertation.controller;

import com.ran.dissertation.ui.MainFrame;
import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainController {

    private MainFrame mainFrame;
    
    private void startApplication() {
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
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new MainController().startApplication();
        });
    }
    
}