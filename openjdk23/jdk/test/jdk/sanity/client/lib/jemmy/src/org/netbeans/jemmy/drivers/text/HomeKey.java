/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.text;

class HomeKey extends OffsetKey {

    public HomeKey(int keyCode, int mods) {
        super(keyCode, mods);
    }

    @Override
    public int getDirection() {
        return -1;
    }

    @Override
    public int getExpectedPosition() {
        return 0;
    }
}
