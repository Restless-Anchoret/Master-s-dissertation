package com.ran.engine.opengl.main;

import com.ran.engine.factories.world.*;
import com.ran.engine.opengl.runner.OpenGLRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class OpenGLMain {

    private static final Logger LOG = LoggerFactory.getLogger(OpenGLMain.class);

    public static void main(String[] args) {
        LOG.trace("OpenGLRunner module main() started");
        OpenGLRunner runner = new OpenGLRunner(Arrays.asList(
                PlaneWorldFactory.getInstance(),
                TangentAnglesWorldFactory.getInstance(),
                BezierWorldFactory.getInstance(),
                InterpolationPresentationWorldFactory.getInstance(),
                DefaultWorldFactory.getInstance()
        ));
        runner.run();
        LOG.trace("OpenGLRunner module main() finished");
    }

}