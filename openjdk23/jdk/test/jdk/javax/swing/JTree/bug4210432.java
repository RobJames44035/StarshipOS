/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
 * @bug 4210432
 * @summary Tests if JTree allows nodes not visible to be selected
 * @run main bug4210432
 */

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class bug4210432 {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JPanel p = new JPanel();
            DefaultMutableTreeNode expansible =
                    new DefaultMutableTreeNode("expansible");
            DefaultMutableTreeNode unexpansible =
                    new DefaultMutableTreeNode("unexpansible");
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
            DefaultMutableTreeNode subexpansible1 =
                    new DefaultMutableTreeNode("sub-expansible 1");
            DefaultMutableTreeNode subexpansible2 =
                    new DefaultMutableTreeNode("sub-expansible 2");
            DefaultMutableTreeNode subsubexpansible1 =
                    new DefaultMutableTreeNode("sub-sub-expansible 1");
            DefaultMutableTreeNode subsubexpansible2 =
                    new DefaultMutableTreeNode("sub-sub-expansible 2");
            expansible.add(subexpansible1);
            expansible.add(subexpansible2);
            subexpansible1.add(subsubexpansible1);
            subexpansible1.add(subsubexpansible2);
            root.add(expansible);
            root.add(unexpansible);
            DefaultTreeModel model = new DefaultTreeModel(root);
            JTree t = new JTree(model);
            Object[] tpa = {root, expansible, subexpansible1};
            Object[] tpa2 = {root, expansible};
            t.setExpandsSelectedPaths(false);
            t.setSelectionPath(new TreePath(tpa));
            p.add(t);
            if (t.isExpanded(new TreePath(tpa2))) {
                throw new RuntimeException("Test failed: JTree should not have " +
                        "expanded path");
            }
            t.clearSelection();
            t.setExpandsSelectedPaths(true);
            t.setSelectionPath(new TreePath(tpa));
            if (!t.isExpanded(new TreePath(tpa2))) {
                throw new RuntimeException("Test failed: JTree should have " +
                        "expanded path");
            }
        });
    }
}
