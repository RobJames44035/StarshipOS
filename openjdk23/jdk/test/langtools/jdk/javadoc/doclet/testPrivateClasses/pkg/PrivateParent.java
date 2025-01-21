/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package pkg;

import java.util.*;

class PrivateParent implements PublicInterface {

    /**
     * Comment from parent.
     */
    public int fieldInheritedFromParent;

    /**
     * Comment from parent.
     *
     * @param p1 param from parent.
     * @throws Exception exception from parent.
     */
    public void methodInheritedFromParent(int p1) throws Exception {
    }

    /**
     * Comment from parent.
     *
     * @param p1 param from parent.
     * @param p2 param from parent.
     * @throws Exception exception from parent.
     */
    public <T extends List, V> PrivateParent methodOverriddenFromParent(
            char[] p1, int p2, T p3, V p4, List<String> p5)
    throws Exception {
        return this;
    }
}
