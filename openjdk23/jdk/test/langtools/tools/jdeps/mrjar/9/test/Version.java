/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package test;

public class Version {
    public int getVersion() {
        NonPublic np = new NonPublic();
        String ignore = np.toString();
        return 9;
    }

    private void foo() {
        if (getVersion() != 9) throw new RuntimeException();
    }
}
