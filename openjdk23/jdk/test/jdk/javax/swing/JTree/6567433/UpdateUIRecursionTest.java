/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 6567433
 *
 * @summary  JTree.updateUI() invokes updateUI() on its TreeCellrenderer via
 * SwingUtilities.updateRendererOrEditorUI().
 * If the TreeCellrenderer is a parent of this JTree, the method recurses
 * endless.
 * This test tests that the fix is effective in avoiding recursion.
 *
 * @run main/othervm UpdateUIRecursionTest
 */

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;

public class UpdateUIRecursionTest extends JFrame implements TreeCellRenderer {
    JTree tree;
    DefaultTreeCellRenderer renderer;

    public UpdateUIRecursionTest() {
        super("UpdateUIRecursionTest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        String[] listData = {
            "First", "Second", "Third", "Fourth", "Fifth", "Sixth"
        };

        tree = new JTree(listData);
        renderer = new DefaultTreeCellRenderer();
        getContentPane().add(new JScrollPane(tree), BorderLayout.CENTER);
        tree.setCellRenderer(this);

        setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                UpdateUIRecursionTest obj = new UpdateUIRecursionTest();

                obj.test();

                obj.disposeUI();
            }
        });
    }

    public void test() {
        tree.updateUI();
    }

    public void disposeUI() {
        setVisible(false);
        dispose();
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                              boolean selected, boolean expanded, boolean leaf,
                              int row, boolean hasFocus)
    {
        return renderer.getTreeCellRendererComponent(tree, value, leaf,
                                                expanded, leaf, row, hasFocus);
    }
}
