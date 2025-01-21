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
        int localVariable;
        localVariable = 101;
        localVariable = 102;
        localVariable = 103;

        while (hs201t001.currentStep <= 4) {
            localVariable = 103;
        }
    }

}
