package com.ran.engine.starter.main;

import com.ran.engine.factories.world.*;
import com.ran.engine.opengl.runner.OpenGLRunner;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        OpenGLRunner runner = new OpenGLRunner(Arrays.asList(
                new AnimationWorldFactory(),
                new PlaneWorldFactory(),
                new TangentAnglesWorldFactory(),
                new BezierWorldFactory(),
                new InterpolationPresentationWorldFactory(),
                new DefaultWorldFactory()
        ));
        runner.init();
        runner.run();
    }

}
