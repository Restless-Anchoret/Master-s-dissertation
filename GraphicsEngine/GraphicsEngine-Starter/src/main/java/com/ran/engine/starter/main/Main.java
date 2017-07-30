package com.ran.engine.starter.main;

import com.ran.engine.factories.world.*;
import com.ran.engine.opengl.runner.OpenGLRunner;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        OpenGLRunner runner = new OpenGLRunner(Arrays.asList(
                PlaneWorldFactory.getInstance(),
                TangentAnglesWorldFactory.getInstance(),
                BezierWorldFactory.getInstance(),
                InterpolationPresentationWorldFactory.getInstance(),
                DefaultWorldFactory.getInstance()
        ));
        runner.run();
    }

}