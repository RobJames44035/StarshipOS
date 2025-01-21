/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package test;

public class Version {
    public int getVersion() {
        return 8;
    }

    private void foo() {
        if (getVersion() != 8) throw new IllegalStateException();
    }
}
