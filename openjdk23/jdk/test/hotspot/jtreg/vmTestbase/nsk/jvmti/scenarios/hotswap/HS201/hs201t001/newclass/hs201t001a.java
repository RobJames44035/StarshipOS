/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.hotswap.HS201;

public class hs201t001a extends Exception {

    public hs201t001a () {
        System.out.println("Current step: " + hs201t001.currentStep); // Avoid calling classloader to find hs201t001 in doInit()
        doInit();
    }

    private void doInit() {
        // Mark that we are in the doInit function.
        hs201t001.isInDoInitFunction = true;

        int localVariable;
        localVariable = 1;
        localVariable = 2;
        localVariable = 3;

        while (hs201t001.currentStep <= 4) {
            localVariable = 3;
        }
    }
}
