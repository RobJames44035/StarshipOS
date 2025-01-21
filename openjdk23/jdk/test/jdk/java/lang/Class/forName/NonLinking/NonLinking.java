/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * @test
 * @bug 8231924 8233091 8233272
 * @summary Confirm load (but not link) behavior of Class.forName()
 * @library /test/lib
 *
 * @compile MissingClass.java Container.java
 *
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar classes.jar Container Container$1
 *
 * @run main/othervm NonLinking init
 * @run main/othervm NonLinking load
 */
/*
 * The @compile and '@main ClassFileInstaller' tasks above create a classes.jar
 * file containing the .class file for Container, but not MissingClass.
 */

public class NonLinking {
    public static void main(String[] args) throws Throwable {
        Path jarPath = Paths.get("classes.jar");
        URL url = jarPath.toUri().toURL();
        URLClassLoader ucl1 = new URLClassLoader("UCL1",
                                                 new URL[] { url },
                                                 null); // Don't delegate
        switch(args[0]) {
            case "init":
                try {
                    // Trying to initialize Container without MissingClass -> NCDFE
                    Class.forName("Container", true, ucl1);
                    throw new RuntimeException("Missed expected NoClassDefFoundError");
                } catch (NoClassDefFoundError expected) {
                    final String CLASSNAME = "MissingClass";
                    Throwable cause = expected.getCause();
                    if (!cause.getMessage().contains(CLASSNAME)) {
                        throw new RuntimeException("Cause of NoClassDefFoundError does not contain \"" + CLASSNAME + "\"", cause);
                    }
                }
                break;
            case "load":
                // Loading (but not linking) Container will succeed.
                // Before 8233091, this fails with NCDFE due to linking.
                Class.forName("Container", false, ucl1);
                break;
            default:
                throw new RuntimeException("Unknown command: " + args[0]);
        }
    }
}
