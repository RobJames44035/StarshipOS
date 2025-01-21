/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */
package com.sun.java.swing.plaf.gtk;

import javax.swing.plaf.synth.Region;

/**
 * A typesafe enumeration of the distinct rendering portions specific
 * to GTK.
 *
 * @author Scott Violet
 */
class GTKRegion extends Region {
    public static final Region HANDLE_BOX = new GTKRegion("HandleBox", null,
                                                          true);

    protected GTKRegion(String name, String ui, boolean subregion) {
        super(name, ui, subregion);
    }
}
