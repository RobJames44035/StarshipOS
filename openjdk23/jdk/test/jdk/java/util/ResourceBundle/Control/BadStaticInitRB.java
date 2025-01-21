/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
/*
 *
 */

import java.util.*;

public class BadStaticInitRB extends ListResourceBundle {
    // Must have an error in this static initializer
    static {
        int[] x = new int[1];
        x[100] = 100;
    }

    public Object[][] getContents() {
        return new Object[][] {
            { "type", "class (static initializer error)" }
        };
    }
}
