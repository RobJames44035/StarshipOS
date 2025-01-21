/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.text;

abstract class OffsetKey extends NavigationKey {

    public OffsetKey(int keyCode, int mods) {
        super(keyCode, mods);
    }

    public abstract int getExpectedPosition();
}
