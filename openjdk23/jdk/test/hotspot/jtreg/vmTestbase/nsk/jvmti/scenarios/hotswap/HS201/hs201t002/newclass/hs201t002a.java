/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.hotswap.HS201;

public class hs201t002a extends Exception {

    public hs201t002a () {
        System.out.println("Current step: " + hs201t002.currentStep); // Avoid calling classloader to find hs201t002 in doInit()
        doInit();
    }

    private void doInit() {
        int localVariable;
        localVariable = 1;
        localVariable = 2;
        localVariable = 3;

        while (hs201t002.currentStep <= 4) {
            localVariable = 3;
        }
    }
}
