package com.ran.engine.opengl.main;

import com.ran.engine.factories.world.DefaultWorldFactory;
import com.ran.engine.factories.world.InterpolationPresentationWorldFactory;
import com.ran.engine.opengl.runner.OpenGLRunner;

import java.util.Arrays;

public class OpenGLMain {

    public static void main(String[] args) {
        OpenGLRunner runner = new OpenGLRunner(Arrays.asList(
                DefaultWorldFactory.getInstance(),
                InterpolationPresentationWorldFactory.getInstance()
        ));
        runner.run();
    }

}