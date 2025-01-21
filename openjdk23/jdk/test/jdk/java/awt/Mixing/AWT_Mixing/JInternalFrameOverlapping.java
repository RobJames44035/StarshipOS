/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */


import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import test.java.awt.regtesthelpers.Util;

/**
 * AWT/Swing overlapping test for {@link javax.swing.JInternalFrame } component.
 * <p>See base class for test info.
 */
/*
 * @test
 * @key headful
 * @summary Overlapping test for javax.swing.JScrollPane
 * @author sergey.grinev@oracle.com: area=awt.mixing
 * @library /java/awt/patchlib  ../../regtesthelpers
 * @modules java.desktop/sun.awt
 *          java.desktop/java.awt.peer
 * @build java.desktop/java.awt.Helper
 * @build Util
 * @run main JInternalFrameOverlapping
 */
public class JInternalFrameOverlapping extends OverlappingTestBase {

    private boolean lwClicked = true;
    private Point lLoc;

    protected boolean performTest() {


        // run robot
        Robot robot = Util.createRobot();
        robot.setAutoDelay(ROBOT_DELAY);

        clickAndBlink(robot, lLoc);

        return lwClicked;
    }

    /**
     * Creating two JInternalFrames in JDesktopPanes. Put lightweight component into one frame and heavyweight into another.
     */
    @Override
    protected void prepareControls() {
        JDesktopPane desktopPane = new JDesktopPane();

        JFrame frame = new JFrame("Test Window");
        frame.setSize(300, 300);
        frame.setContentPane(desktopPane);
        frame.setVisible(true);
        JInternalFrame bottomFrame = new JInternalFrame("bottom frame", false, false, false, false);
        bottomFrame.setSize(220, 220);
        desktopPane.add(bottomFrame);
        bottomFrame.setVisible(true);

        super.propagateAWTControls(bottomFrame);
        JInternalFrame topFrame = new JInternalFrame("top frame", false, false, false, false);
        topFrame.setSize(200, 200);
        JButton jbutton = new JButton("LW Button") {{
                addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        lwClicked = true;
                    }
                });
            }};
        topFrame.add(jbutton);
        desktopPane.add(topFrame);
        topFrame.setVisible(true);
        lLoc = jbutton.getLocationOnScreen();
        lLoc.translate(jbutton.getWidth()/2, jbutton.getWidth()/2); //click at middle of the button
    }

    // this strange plumbing stuff is required due to "Standard Test Machinery" in base class
    public static void main(String args[]) throws InterruptedException {
        instance = new JInternalFrameOverlapping();
        OverlappingTestBase.doMain(args);
    }

}
