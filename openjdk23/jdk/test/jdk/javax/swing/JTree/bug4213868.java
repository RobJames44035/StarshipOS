/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
 * @bug 4213868
 * @summary Tests if AccessibleJTreeNode.getAccessibleIndexInParent() returns
 * correct value
 * @run main bug4213868
 */

import javax.accessibility.AccessibleContext;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

public class bug4213868 {
    public static JTree createTree() {
        DefaultMutableTreeNode root =
                new DefaultMutableTreeNode(0, true);
        JTree tree = new JTree(root);
        for (int i = 1; i < 10; i++) {
            root.add(new DefaultMutableTreeNode(i));
        }
        return tree;
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JTree parent = createTree();
            AccessibleContext c = parent.getAccessibleContext()
                                        .getAccessibleChild(0)
                                        .getAccessibleContext();
            if (c.getAccessibleChild(1)
                 .getAccessibleContext()
                 .getAccessibleIndexInParent() != 1) {
                throw new RuntimeException("Test failed: " +
                        "AccessibleJTreeNode.getAccessibleIndexInParent() " +
                        "returns incorrect value");
            }
        });
    }
}
