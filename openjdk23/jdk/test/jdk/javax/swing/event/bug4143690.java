/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
   @bug 4143690
   @summary Tests that TreeSelectionEvent has isAddedPath(int) method
   @run main bug4143690
*/

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

public class bug4143690 {

    public static void main(String[] argv) throws Exception {
        bug4143690 test = new bug4143690();
        TreePath p = new TreePath("");
        TreeSelectionEvent e = new TreeSelectionEvent(test, p, true, p, p);

        TreePath[] paths = e.getPaths();
        for(int i = 0; i < paths.length; i++) {
            TreePath path = paths[i];
            if (e.isAddedPath(i) != true) {
                throw new RuntimeException("Incorrect isAddedPath(int)...");
            }
        }
    }
}
