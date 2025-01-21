/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.io.File;

/*
 * @test
 * @run main/othervm -Xbootclasspath/a:/usr/lib -showversion -Xbootclasspath/a:/i/dont/exist BootClassPathAppendProp
 * @run main/othervm --patch-module=no_module=/not/here -Xbootclasspath/a:/i/may/exist BootClassPathAppendProp
 * @run main/othervm -Djdk.boot.class.path.append=newdir BootClassPathAppendProp
 * @run main/othervm BootClassPathAppendProp
 */

// Test that property jdk.boot.class.path.append contains only the bootclasspath
// info following the "modules" jimage file.
public class BootClassPathAppendProp {
    public static void main(String[] args) throws Exception {
        // jdk.boot.class.path.append is a non-writeable, internal property.
        // The call to System.getProperty should return null.
        if (System.getProperty("jdk.boot.class.path.append") != null) {
            throw new RuntimeException("Test failed, jdk.boot.class.path.append has value: " +
                System.getProperty("jdk.boot.class.path.append"));
        } else {
            System.out.println("Test BootClassPathAppendProp passed");
        }
    }
}
