/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6402062 6487891
 * @summary Tests TreePath encoding
 * @run main/othervm javax_swing_tree_TreePath
 * @author Sergey Malenkov
 */

import javax.swing.tree.TreePath;

public final class javax_swing_tree_TreePath extends AbstractTest<TreePath> {
    public static void main(String[] args) {
        new javax_swing_tree_TreePath().test();
    }

    protected TreePath getObject() {
        return new TreePath("SinglePath");
    }

    protected TreePath getAnotherObject() {
        return new TreePath(new String[] {"First", "Second"});
    }
}
