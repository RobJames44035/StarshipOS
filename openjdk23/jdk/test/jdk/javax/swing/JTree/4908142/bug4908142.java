/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */


/*
 * @test
 * @key headful
 * @bug 4908142
 * @summary JList doesn't handle search function appropriately
 * @author Andrey Pikalev
 * @library ../../regtesthelpers
 * @build Util
 * @run main bug4908142
 */
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.Callable;

public class bug4908142 {

    private static JFrame fr = null;
    private static JTree tree = null;

    public static void main(String[] args) throws Exception {

        Robot robot = new Robot();
        robot.setAutoDelay(50);

        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    createAndShowGUI();
                }
            });

            robot.waitForIdle();
            robot.delay(1000);

            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    tree.requestFocus();
                    tree.setSelectionRow(0);
                }
            });

            robot.waitForIdle();
            robot.delay(500);

            robot.keyPress(KeyEvent.VK_A);
            robot.keyRelease(KeyEvent.VK_A);
            robot.waitForIdle();
            robot.keyPress(KeyEvent.VK_A);
            robot.keyRelease(KeyEvent.VK_A);
            robot.waitForIdle();
            robot.keyPress(KeyEvent.VK_D);
            robot.keyRelease(KeyEvent.VK_D);
            robot.waitForIdle();


            String sel = Util.invokeOnEDT(new Callable<String>() {

                @Override
                public String call() throws Exception {
                    return tree.getLastSelectedPathComponent().toString();
                }
            });

            if (!"aad".equals(sel)) {
                throw new Error("The selected index should be \"aad\", but not " + sel);
            }
        } finally {
            if (fr != null) {
                SwingUtilities.invokeAndWait(fr::dispose);
            }
        }
    }

    private static void createAndShowGUI() {
        fr = new JFrame("Test");
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] data = {"aaa", "aab", "aac", "aad", "ade", "bba"};
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode(data[0]);
        for (int i = 1; i < data.length; i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(data[i]);
            root.add(node);
        }

        tree = new JTree(root);

        JScrollPane sp = new JScrollPane(tree);
        fr.getContentPane().add(sp);
        fr.setLocationRelativeTo(null);
        fr.setSize(200, 200);
        fr.setVisible(true);
    }
}
