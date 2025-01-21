/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 8004298
 * @requires (os.family == "windows")
 * @summary NPE in WindowsTreeUI.ensureRowsAreVisible
 * @author Alexander Scherbatiy
 * @library ../../regtesthelpers
 * @modules java.desktop/com.sun.java.swing.plaf.windows
 * @build Util
 * @run main bug8004298
 */

import java.awt.*;
import java.awt.event.InputEvent;
import javax.swing.*;
import javax.swing.tree.*;
import java.util.concurrent.Callable;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsTreeUI;

public class bug8004298 {

    private static JTree tree;

    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        robot.setAutoDelay(50);
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (javax.swing.UnsupportedLookAndFeelException ulafe) {
            System.out.println(ulafe.getMessage());
            System.out.println("The test is considered PASSED");
            return;
        }
        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                createAndShowGUI();
            }
        });

        robot.waitForIdle();

        Point point = Util.invokeOnEDT(new Callable<Point>() {

            @Override
            public Point call() throws Exception {
                Rectangle rect = tree.getRowBounds(2);
                Point p = new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);
                SwingUtilities.convertPointToScreen(p, tree);
                return p;
            }
        });

        robot.mouseMove(point.x, point.y);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.waitForIdle();

    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        root.add(new DefaultMutableTreeNode("colors"));
        DefaultMutableTreeNode sports = new DefaultMutableTreeNode("sports");
        sports.add(new DefaultMutableTreeNode("basketball"));
        sports.add(new DefaultMutableTreeNode("football"));
        root.add(sports);

        tree = new JTree(root);
        tree.setUI(new NullReturningTreeUI());

        frame.getContentPane().add(tree);
        frame.pack();
        frame.setVisible(true);

    }

    private static final class NullReturningTreeUI extends WindowsTreeUI {

        @Override
        public Rectangle getPathBounds(JTree tree, TreePath path) {
            // the method can return null and callers have to be ready for
            // that. Simulate the case by returning null for unknown reason.
            if (path != null && path.toString().contains("football")) {
                return null;
            }

            return super.getPathBounds(tree, path);
        }
    }
}
