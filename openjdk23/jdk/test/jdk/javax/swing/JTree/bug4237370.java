/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
 * @bug 4237370
 * @summary Tests that JTree calls TreeExpansionListener methods
 *          after it has been updated due to expanded/collapsed event
 * @run main bug4237370
 */

import java.lang.reflect.InvocationTargetException;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class bug4237370 {
    static class TestTree extends JTree implements TreeExpansionListener {
        int[] testMap = {1, 2};
        int testIndex = 0;

        private void testRowCount() {
            int rows = getRowCount();
            if (rows != testMap[testIndex]) {
                throw new RuntimeException("Bad row count: reported " + rows +
                                " instead of " + testMap[testIndex]);
            } else {
                testIndex++;
            }
        }

        public void treeExpanded(TreeExpansionEvent e) {
            testRowCount();
        }

        public void treeCollapsed(TreeExpansionEvent e) {
            testRowCount();
        }

        public TestTree() {
            super((TreeModel)null);
            DefaultMutableTreeNode top = new DefaultMutableTreeNode("Root");
            top.add(new DefaultMutableTreeNode("Sub 1"));
            setModel(new DefaultTreeModel(top));
            addTreeExpansionListener(this);
        }
    }

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {
        SwingUtilities.invokeAndWait(() -> {
            TestTree tree = new TestTree();
            tree.collapseRow(0);
            tree.expandRow(0);
        });
    }
}
