/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package nsk.jvmti.AttachOnDemand.attach046;

public class ClassToRedefine {

    private String version;

    public String getVersion() {
        version= "1.0";
        return version;
    }
}
