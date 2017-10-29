package com.ran.engine.factories.world;

import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.quaternion.Quaternion;
import com.ran.engine.algebra.vector.ThreeDoubleVector;
import com.ran.engine.factories.animations.AffineTransformationFactory;
import com.ran.engine.factories.animations.AnimationFactory;
import com.ran.engine.factories.figures.DemonstrationFiguresFactory;
import com.ran.engine.factories.figures.FigureFactory;
import com.ran.engine.factories.figures.InterpolatedFiguresFactory;
import com.ran.engine.rendering.control.AnimationControl;
import com.ran.engine.rendering.control.Control;
import com.ran.engine.rendering.control.DoubleParameterControl;
import com.ran.engine.rendering.control.IntegerParameterControl;
import com.ran.engine.rendering.world.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.ran.engine.factories.constants.CommonConstants.DARK_GRAY_COLOR;

public abstract class BaseWorldFactory extends AbstractWorldFactory {

    private FigureFactory figureFactory = new FigureFactory();
    private DemonstrationFiguresFactory demonstrationFiguresFactory = new DemonstrationFiguresFactory();
    private InterpolatedFiguresFactory interpolatedFiguresFactory = new InterpolatedFiguresFactory();
    private AnimationFactory animationFactory = new AnimationFactory();
    private AffineTransformationFactory affineTransformationFactory = new AffineTransformationFactory();

    protected FigureFactory getFigureFactory() {
        return figureFactory;
    }

    protected DemonstrationFiguresFactory getDemonstrationFiguresFactory() {
        return demonstrationFiguresFactory;
    }

    protected InterpolatedFiguresFactory getInterpolatedFiguresFactory() {
        return interpolatedFiguresFactory;
    }

    protected AnimationFactory getAnimationFactory() {
        return animationFactory;
    }

    protected AffineTransformationFactory getAffineTransformationFactory() {
        return affineTransformationFactory;
    }

    protected WorldObjectCreator plainGridCreator(int xCellsQuantity, int yCellsQuantity,
                                                  double xCellWidth, double yCellWidth,
                                                  Orientation orientation, boolean isVertical) {
        return () -> {
            AnimationInfo animationInfo = new AnimationInfoBuilder()
                    .setInitialOrientation(orientation).build();
            Figure figure = (isVertical ? figureFactory.makeVerticalPlainGrid(xCellsQuantity, yCellsQuantity, xCellWidth, yCellWidth) :
                figureFactory.makePlainGrid(xCellsQuantity, yCellsQuantity, xCellWidth, yCellWidth));
            WorldObjectPart grid = new WorldObjectPartBuilder()
                    .setFigure(figure)
                    .setColor(DARK_GRAY_COLOR).build();
            return new WorldObjectContent(animationInfo, Collections.singletonList(grid));
        };
    }

    protected WorldObjectCreator plainGridCreator(int xCellsQuantity, int yCellsQuantity,
                                                  double xCellWidth, double yCellWidth,
                                                  Orientation orientation) {
        return plainGridCreator(xCellsQuantity, yCellsQuantity, xCellWidth, yCellWidth, orientation, false);
    }

    protected WorldObjectCreator plainGridCreator(int xCellsQuantity, int yCellsQuantity,
                                                  double xCellWidth, double yCellWidth) {
        return plainGridCreator(xCellsQuantity, yCellsQuantity, xCellWidth, yCellWidth,
                Orientation.INITIAL_ORIENTATION, false);
    }

    protected WorldObjectCreator plainGridWithControlsCreator(int xCellsQuantity, int yCellsQuantity,
                                                              double xCellWidth, double yCellWidth,
                                                              Orientation orientation) {
        IntegerParameterControl xCellsQuantityControl =
                new IntegerParameterControl(1, 100, xCellsQuantity);
        IntegerParameterControl yCellsQuantityControl =
                new IntegerParameterControl(1, 100, yCellsQuantity);
        DoubleParameterControl xCellWidthControl =
                new DoubleParameterControl(0.5, 5.0, xCellWidth, 0.1);
        DoubleParameterControl yCellWidthControl =
                new DoubleParameterControl(0.5, 5.0, yCellWidth, 0.1);
        return new WorldObjectCreator() {
            @Override
            public WorldObjectContent create() {
                AnimationInfo animationInfo = new AnimationInfoBuilder()
                        .setInitialOrientation(orientation).build();
                WorldObjectPart grid = new WorldObjectPartBuilder()
                        .setFigure(figureFactory.makePlainGrid(
                                xCellsQuantityControl.getControlValue(),
                                yCellsQuantityControl.getControlValue(),
                                xCellWidthControl.getControlValue(),
                                yCellWidthControl.getControlValue()))
                        .setColor(DARK_GRAY_COLOR).build();
                return new WorldObjectContent(animationInfo, Collections.singletonList(grid));
            }
            @Override
            public List<Control> getControls() {
                return Arrays.asList(xCellsQuantityControl, yCellsQuantityControl,
                        xCellWidthControl, yCellWidthControl);
            }
        };
    }

    protected WorldObjectCreator plainGridWithControlsCreator(int xCellsQuantity, int yCellsQuantity,
                                                              double xCellWidth, double yCellWidth) {
        return plainGridWithControlsCreator(xCellsQuantity, yCellsQuantity, xCellWidth, yCellWidth,
                Orientation.INITIAL_ORIENTATION);
    }

    protected WorldObjectCreator fixedObjectCreator(List<WorldObjectPart> worldObjectParts, Orientation orientation) {
        return () -> new WorldObjectContent(
                new AnimationInfoBuilder().setInitialOrientation(orientation).build(),
                worldObjectParts);
    }

    protected WorldObjectCreator fixedObjectCreator(WorldObjectPart worldObjectPart, Orientation orientation) {
        return fixedObjectCreator(Collections.singletonList(worldObjectPart), orientation);
    }

    protected WorldObjectCreator animatedObjectCreator(AnimationInfo animationInfo, List<WorldObjectPart> worldObjectParts) {
        AnimationControl animationControl = new AnimationControl();
        return new WorldObjectCreator() {
            @Override
            public WorldObjectContent create() {
                return new WorldObjectContent(animationInfo, worldObjectParts);
            }
            @Override
            public List<Control> getControls() {
                return Collections.singletonList(animationControl);
            }
        };
    }

    protected WorldObjectCreator animatedObjectCreator(AnimationInfo animationInfo, WorldObjectPart worldObjectPart) {
        return animatedObjectCreator(animationInfo, Collections.singletonList(worldObjectPart));
    }

    protected WorldObjectPart createStandardGlobe(double radius) {
        return new WorldObjectPartBuilder()
                .setFigure(getFigureFactory().makeGlobe(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, radius, 12))
                .setColor(DARK_GRAY_COLOR)
                .setVerticePaintRadius(0)
                .build();
    }

    protected List<WorldObjectCreator> animationPresentationObjectCreators(
            Orientation orientation,
            List<Quaternion> quaternions,
            Supplier<DoubleFunction<Quaternion>> functionSupplier) {
        List<WorldObjectCreator> worldObjectCreators = new ArrayList<>();
        Figure figureForRotation = getFigureFactory().makeAeroplane(1.5);

        for (Quaternion quaternion: quaternions) {
            worldObjectCreators.add(fixedObjectCreator(new WorldObjectPartBuilder()
                            .setFigure(figureForRotation).setColor(Color.RED).build(),
                    new Orientation(orientation.getOffset(), quaternion)));
        }

        worldObjectCreators.add(animatedObjectCreator(
                new AnimationInfoBuilder()
                        .setAnimationFunctionAndOffset(functionSupplier.get(), orientation.getOffset())
                        .setAnimationCyclic(false).build(),
                new WorldObjectPartBuilder()
                        .setFigure(figureForRotation)
                        .setEdgePaintWidth(2.0f).build()
        ));
        return worldObjectCreators;
    }

}
