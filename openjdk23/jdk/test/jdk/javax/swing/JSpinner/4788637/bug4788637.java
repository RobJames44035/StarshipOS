/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import java.io.File;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;

import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.imageio.ImageIO;
import static javax.swing.UIManager.getInstalledLookAndFeels;


/**
 * @test
 * @bug 4788637 7124307
 * @key headful
 * @summary JSpinner buttons don't conform to most platform conventions
 */
public final class bug4788637 {

    private static JSpinner spinner;
    private static JFrame fr;

    private static Robot robot;
    private int step = 0;
    private volatile boolean spinnerValueChanged[] = {false, false, false};

    private static volatile Point p;
    private static volatile Rectangle rect;

    public static void main(final String[] args) throws Exception {
        robot = new Robot();
        robot.setAutoDelay(100);
        robot.setAutoWaitForIdle(true);
        for (final UIManager.LookAndFeelInfo laf : getInstalledLookAndFeels()) {
            SwingUtilities.invokeAndWait(() -> setLookAndFeel(laf));
            bug4788637 app = new bug4788637();
            try {
                SwingUtilities.invokeAndWait(app::createAndShowGUI);
                robot.waitForIdle();
                robot.delay(1000);
                SwingUtilities.invokeAndWait(()-> {
                    spinner.requestFocus();
                    p = spinner.getLocationOnScreen();
                    rect = spinner.getBounds();
                });
                app.start();
            } finally {
                SwingUtilities.invokeAndWait(app::destroy);
            }
        }
    }

    public void createAndShowGUI() {
        fr = new JFrame("Test");
        fr.setLayout( new GridBagLayout() );

        SpinnerModel model = new SpinnerNumberModel(50, 1, 100, 1);
        spinner = new JSpinner(model);
        fr.add(spinner,new GridBagConstraints());

        spinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                synchronized (bug4788637.this) {
                    spinnerValueChanged[step] = true;
                    bug4788637.this.notifyAll();
                }
            }
        });

        fr.setSize(200, 200);
        fr.setLocationRelativeTo(null);
        fr.setVisible(true);
        fr.toFront();
    }

    public void start() {
        try {
            Thread.sleep(1000);
            System.out.println("p " + p + " rect " + rect);
            // Move mouse to the up arrow button
            robot.mouseMove(p.x+rect.width-3, p.y+3);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

            synchronized (bug4788637.this) {
                if (!spinnerValueChanged[step]) {
                    bug4788637.this.wait(3000);
                }
            }

            // Move mouse out of JSpinner
            robot.mouseMove(p.x+rect.width-3, p.y-3);
            synchronized (bug4788637.this) {
                step++;
                if (!spinnerValueChanged[step]) {
                    bug4788637.this.wait(3000);
                }
            }
            robot.waitForIdle();

            // Move mouse to the up arrow button
            robot.mouseMove(p.x+rect.width-3, p.y+3);
            synchronized (bug4788637.this) {
                step++;
                if (!spinnerValueChanged[step]) {
                    bug4788637.this.wait(3000);
                }
            }
            robot.waitForIdle();

            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.waitForIdle();
        } catch(Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public void destroy() {
        fr.dispose();
        synchronized (bug4788637.this) {
            if (!spinnerValueChanged[0] ||
                    spinnerValueChanged[1] ||
                    !spinnerValueChanged[2]) {
                System.out.println("!spinnerValueChanged[0] " + !spinnerValueChanged[0] +
                                   " spinnerValueChanged[1] " + spinnerValueChanged[1] +
                                   " !spinnerValueChanged[2] " + !spinnerValueChanged[2]);
                try {
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    Rectangle screen = new Rectangle(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
                    BufferedImage fullScreen = robot.createScreenCapture(screen);
                    ImageIO.write(fullScreen, "png", new File("fullScreen.png"));
                } catch (Exception e) {}
                throw new Error("JSpinner buttons don't conform to most platform conventions");
            }
        }
    }

    private static void setLookAndFeel(final UIManager.LookAndFeelInfo laf) {
        try {
            UIManager.setLookAndFeel(laf.getClassName());
            System.out.println("LookAndFeel: " + laf.getClassName());
        } catch (ClassNotFoundException | InstantiationException |
                UnsupportedLookAndFeelException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
