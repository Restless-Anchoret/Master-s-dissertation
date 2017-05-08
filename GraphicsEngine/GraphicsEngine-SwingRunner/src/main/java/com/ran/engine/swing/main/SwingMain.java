package com.ran.engine.swing.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwingMain {

    private static final Logger LOG = LoggerFactory.getLogger(SwingMain.class);

    public static void main(String[] args) {
        LOG.trace("SwingRunner module main() started");
        System.out.println("SwingMain main()-method");
        LOG.trace("SwingRunner module main() finished");
    }

}