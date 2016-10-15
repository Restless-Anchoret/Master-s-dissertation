package com.ran.interpolation;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LabFrame extends JFrame {

    private static final double ANGLE_STEP = Math.PI / 36.0,
            SIZE_STEP = 1.2,
            MOVE_STEP = 0.25;

    private DrawPanel drawPanel = null;

    public LabFrame() {
        Figure figure = Figure.figureFromFile("values/figure.txt");
        Camera camera = Camera.cameraFromFile("values/camera.txt");
        drawPanel = new DrawPanel(figure, camera);

        initUserInterface();

        setTitle("Computer graphics Lab1");
        setResizable(false);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dim = getSize();
        setLocation(new Point((screen.width - dim.width) / 2, (screen.height - dim.height) / 2));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initUserInterface() {
        setLayout(new BorderLayout());
        add(drawPanel, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(21, 1));

        JButton def = new JButton("Default");
        def.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                drawPanel.getFigure().defaultAffine();
                drawPanel.getCamera().defaultPosition();
                drawPanel.repaint();
            }
        });
        panel.add(def);

        panel.add(new JLabel("Chosen vertice:"));
        JComboBox<String> combo = new JComboBox<>();
        combo.addItem("Not chosen");
        for (int i = 1; i <= drawPanel.getFigure().getVerticesQuantity(); i++) {
            combo.addItem("" + i);
        }
        combo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox<String> box = (JComboBox<String>) event.getSource();
                String str = (String) box.getSelectedItem();
                if (str.equals("Not chosen")) {
                    drawPanel.setCurrentVertice(-1);
                } else {
                    drawPanel.setCurrentVertice(Integer.parseInt(str) - 1);
                }
                drawPanel.repaint();
            }
        });
        panel.add(combo);

        panel.add(new JLabel("Scaling"));
        JPanel panelScaling = new JPanel();
        panelScaling.setLayout(new BoxLayout(panelScaling, BoxLayout.X_AXIS));
        panelScaling.add(makeButton("Increase", Affine.affineScaling(SIZE_STEP)));
        panelScaling.add(makeButton("Decrease", Affine.affineScaling(1.0 / SIZE_STEP)));
        panel.add(panelScaling);

        panel.add(new JLabel("Moving"));
        JPanel panelMovingX = new JPanel();
        panelMovingX.setLayout(new BoxLayout(panelMovingX, BoxLayout.X_AXIS));
        panelMovingX.add(new JLabel("X: "));
        panelMovingX.add(makeButton("Minus", Affine.affineTransfer(-MOVE_STEP, 0.0, 0.0)));
        panelMovingX.add(makeButton("Plus", Affine.affineTransfer(MOVE_STEP, 0.0, 0.0)));
        panel.add(panelMovingX);

        JPanel panelMovingY = new JPanel();
        panelMovingY.setLayout(new BoxLayout(panelMovingY, BoxLayout.X_AXIS));
        panelMovingY.add(new JLabel("Y: "));
        panelMovingY.add(makeButton("Minus", Affine.affineTransfer(0.0, -MOVE_STEP, 0.0)));
        panelMovingY.add(makeButton("Plus", Affine.affineTransfer(0.0, MOVE_STEP, 0.0)));
        panel.add(panelMovingY);

        JPanel panelMovingZ = new JPanel();
        panelMovingZ.setLayout(new BoxLayout(panelMovingZ, BoxLayout.X_AXIS));
        panelMovingZ.add(new JLabel("Z: "));
        panelMovingZ.add(makeButton("Minus", Affine.affineTransfer(0.0, 0.0, -MOVE_STEP)));
        panelMovingZ.add(makeButton("Plus", Affine.affineTransfer(0.0, 0.0, MOVE_STEP)));
        panel.add(panelMovingZ);

        panel.add(new JLabel("Rotation"));
        JPanel panelRotationX = new JPanel();
        panelRotationX.setLayout(new BoxLayout(panelRotationX, BoxLayout.X_AXIS));
        panelRotationX.add(new JLabel("X: "));
        panelRotationX.add(makeButton("Counterclockwise", Affine.affineRotationX(ANGLE_STEP)));
        panelRotationX.add(makeButton("Clockwise", Affine.affineRotationX(-ANGLE_STEP)));
        panel.add(panelRotationX);

        JPanel panelRotationY = new JPanel();
        panelRotationY.setLayout(new BoxLayout(panelRotationY, BoxLayout.X_AXIS));
        panelRotationY.add(new JLabel("Y: "));
        panelRotationY.add(makeButton("Counterclockwise", Affine.affineRotationY(ANGLE_STEP)));
        panelRotationY.add(makeButton("Clockwise", Affine.affineRotationY(-ANGLE_STEP)));
        panel.add(panelRotationY);

        JPanel panelRotationZ = new JPanel();
        panelRotationZ.setLayout(new BoxLayout(panelRotationZ, BoxLayout.X_AXIS));
        panelRotationZ.add(new JLabel("Z: "));
        panelRotationZ.add(makeButton("Counterclockwise", Affine.affineRotationZ(ANGLE_STEP)));
        panelRotationZ.add(makeButton("Clockwise", Affine.affineRotationZ(-ANGLE_STEP)));
        panel.add(panelRotationZ);

        panel.add(new JLabel("Reflection"));
        JPanel panelReflectionLine = new JPanel(new GridLayout(1, 3));
        panelReflectionLine.add(makeButton("By X", Affine.affineReflectionX()));
        panelReflectionLine.add(makeButton("By Y", Affine.affineReflectionY()));
        panelReflectionLine.add(makeButton("By Z", Affine.affineReflectionZ()));
        panel.add(panelReflectionLine);

        JPanel panelReflectionPlane = new JPanel(new GridLayout(1, 3));
        panelReflectionPlane.add(makeButton("By XOY", Affine.affineReflectionXY()));
        panelReflectionPlane.add(makeButton("By YOZ", Affine.affineReflectionYZ()));
        panelReflectionPlane.add(makeButton("By ZOX", Affine.affineReflectionZX()));
        panel.add(panelReflectionPlane);

        panel.add(makeButton("By (0, 0)", Affine.affineReflection()));

        panel.add(new JLabel("Smoothing"));
        JButton buttonSmoothing = new JButton("Smoothing on/off");
        buttonSmoothing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPanel.setSmoothing(!drawPanel.isSmoothing());
            }
        });
        panel.add(buttonSmoothing);

        panel.add(new JLabel("Distance"));
        int value = (int) (drawPanel.getCamera().getDistanceReverse() * 100);
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 200, value);
        slider.setPreferredSize(def.getPreferredSize());
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                drawPanel.getCamera().setDistanceReverse(((JSlider) e.getSource()).getValue() / 100.0);
                drawPanel.repaint();
            }
        });
        panel.add(slider);

        JPanel extra = new JPanel();
        extra.add(panel);
        add(extra, BorderLayout.EAST);

        pack();
    }

    private JButton makeButton(String name, Affine affine) {
        JButton button = new JButton(name);
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Point3D vertice = drawPanel.getCurrentVertice();
                drawPanel.getFigure().applyAffine(affine.overPoint(vertice));
                drawPanel.repaint();
            }
        };
        button.addActionListener(listener);
        return button;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                }
                LabFrame frame = new LabFrame();
            }
        });
    }

}
