package com.ran.dissertation.controller;

import com.ran.dissertation.ui.MainFrame;
import java.awt.EventQueue;
import java.util.logging.Level;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainController {

    private MainFrame mainFrame;
    
    private void startApplication() {
        EventQueue.invokeLater(() -> {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | InstantiationException |
                    IllegalAccessException | UnsupportedLookAndFeelException exception) { }
            mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
    
    public static void main(String[] args) {
        new MainController().startApplication();
    }
    
}