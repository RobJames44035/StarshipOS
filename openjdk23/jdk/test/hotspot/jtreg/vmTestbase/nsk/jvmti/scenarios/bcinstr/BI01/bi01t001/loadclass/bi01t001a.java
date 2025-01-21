/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.bcinstr.BI01;

public class bi01t001a {

    public static int additionalValue = 0;

    public static int methodA() {
        //The below line is commented in newclass
        additionalValue = 0;

        //The below line is uncommented in newclass
        //additionalValue = 1;

        int returnValue = 100 + additionalValue;
        return returnValue;
    }
}
