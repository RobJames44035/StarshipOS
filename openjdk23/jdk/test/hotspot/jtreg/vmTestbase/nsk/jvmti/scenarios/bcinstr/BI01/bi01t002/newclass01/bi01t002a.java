/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.bcinstr.BI01;

public class bi01t002a {

    static int additionalValue = 0;

    static public int methodA() {
        //The below line is commented in newclass01 and newclass02
        //additionalValue = 0;

        //The below line is uncommented in newclass01
        additionalValue = 1;

        //The below line is uncommented in newclass02
        //additionalValue = 2;

        int returnValue = 100 + additionalValue;
        return returnValue;
    }
}
