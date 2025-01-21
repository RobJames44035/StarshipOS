/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package pkg;

import java.util.*;

public class PublicChild extends PrivateParent
    implements PrivateInterface {

    public <T extends List, V> PublicChild methodOverriddenFromParent(
            char[] p1, int p2, T p3, V p4, List<String> p5)
    throws Exception {
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param p1 {@inheritDoc}
     * @throws Exception {@inheritDoc}
     */
    public void methodInterface(int p1) throws Exception {
    }

    public void methodInterface2(int p1) throws Exception {
    }
}
