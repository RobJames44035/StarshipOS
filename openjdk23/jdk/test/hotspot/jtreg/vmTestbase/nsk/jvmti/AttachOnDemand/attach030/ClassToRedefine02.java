/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package nsk.jvmti.AttachOnDemand.attach030;

public class ClassToRedefine02 {
    public String getString() {
        /*
         * In the redefined version method returns "ClassToRedefine01: Class is redefined"
         */
        return "ClassToRedefine02: Class isn't redefined";
    }
}
