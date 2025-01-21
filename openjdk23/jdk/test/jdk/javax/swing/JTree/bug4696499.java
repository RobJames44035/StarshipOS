/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4696499
 * @summary new tree model asked about nodes of previous tree model
 * @run main bug4696499
 */

import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class bug4696499 {
    public static void main(String[] args) throws Exception {
        JTree tree = new JTree();
        TreeModel model = new MyModel();
        tree.setModel(model);

        tree.setSelectionRow(1);
        model = new MyModel();
        tree.setModel(model);
    }
}

class MyModel implements TreeModel {
    private Object root = "Root";
    private ArrayList listeners = new ArrayList();
    private TreeNode ONE;
    static int next = 1;

    MyModel() {
        ONE = new DefaultMutableTreeNode(next);
        next *= 2;
    }

    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);
    }

    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }

    public void valueForPathChanged(TreePath tp, Object newValue) {
    }

    public Object getRoot() {
        return root;
    }

    public boolean isLeaf(Object o) {
        return o == ONE;
    }

    public int getIndexOfChild(Object parent, Object child) {
        if (parent != root || child != ONE) {
            throw new RuntimeException("This method is called with the child " +
                    "of the previous tree model");
        }
        return 0;
    }

    public int getChildCount(Object o) {
        if (o == root) {
            return 1;
        }
        if (o == ONE) {
            return 0;
        }
        throw new IllegalArgumentException(o.toString());
    }

    public Object getChild(Object o, int index) {
        if (o != root || index != 0) {
            throw new IllegalArgumentException(o + ", " + index);
        }
        return ONE;
    }
}
