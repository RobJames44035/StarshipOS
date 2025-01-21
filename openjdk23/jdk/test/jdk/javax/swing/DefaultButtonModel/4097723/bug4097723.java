/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4097723
 * @summary Tests that method DefaultButtonModel.getGroup() exists
 * @run main bug4097723
 */

import javax.swing.ButtonGroup;
import javax.swing.DefaultButtonModel;

public class bug4097723 {
    public static void main(String[] argv) {
        DefaultButtonModel dbm = new DefaultButtonModel();
        ButtonGroup group = new ButtonGroup();
        dbm.setGroup(group);
        ButtonGroup g = dbm.getGroup();
        if (g != group) {
            throw new RuntimeException("Failure: getGroup() returned wrong thing");
        }
    }
}
