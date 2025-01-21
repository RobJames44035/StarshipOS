/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4210354
 * @summary Tests whether method FixedHeightLayoutCache.getBounds returns bad Rectangle
 * @run main bug4210354
 */

import java.awt.Rectangle;

import javax.swing.tree.AbstractLayoutCache;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.FixedHeightLayoutCache;
import javax.swing.tree.TreePath;

public class bug4210354 {
    static class DummyNodeDimensions extends AbstractLayoutCache.NodeDimensions {
        private final Rectangle rectangle;

        public DummyNodeDimensions(Rectangle r) {
            rectangle = r;
        }
        public Rectangle getNodeDimensions(Object value, int row, int depth,
                                           boolean expanded, Rectangle bounds) {
            return rectangle;
        }

        /* create the TreeModel of depth 1 with specified num of children */
        public DefaultTreeModel getTreeModelILike(int childrenCount) {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
            for (int i = 0; i < childrenCount; i++) {
                DefaultMutableTreeNode child =
                        new DefaultMutableTreeNode("root.child" + i);
                root.insert(child, i);
            }
            return new DefaultTreeModel(root);
        }
    }

    public void init() {
        int x = 1, y = 2, dx = 3, dy = 4, h = 3;
        DummyNodeDimensions dim = new DummyNodeDimensions(new Rectangle(x, y, dx, dy));
        FixedHeightLayoutCache fhlc = new FixedHeightLayoutCache();
        fhlc.setModel(dim.getTreeModelILike(3));
        fhlc.setRootVisible(true);
        fhlc.setNodeDimensions(dim);
        fhlc.setRowHeight(h);
        int row = 0;
        TreePath path = fhlc.getPathForRow(row);
        Rectangle r = fhlc.getBounds(path, new Rectangle());
        Rectangle r2 = new Rectangle(x, row * h, dx, h);
        if (r.width != r2.width) {
            throw new RuntimeException("FixedHeightLayoutCache.getBounds returns bad Rectangle");
        }
    }

    public static void main(String[] args) throws Exception {
        bug4210354 b = new bug4210354();
        b.init();
    }
}
