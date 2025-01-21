/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6866479
 * @summary libzip.so caused JVM to crash when running jarsigner
 * @library /test/lib
 */

import jdk.test.lib.Platform;
import jdk.test.lib.SecurityTools;
import jdk.test.lib.util.JarUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SameName {
    public static void main(String[] args) throws Exception {

        String signedJar = Platform.isWindows() ? "EM.jar" : "em.jar";

        Files.write(Path.of("A"), List.of("A"));
        JarUtils.createJarFile(Path.of("em.jar"), Path.of("."), Path.of("A"));

        SecurityTools.keytool("-storepass changeit -keypass changeit "
                + "-keystore ks -keyalg rsa  -alias a -dname CN=a "
                + "-keyalg rsa -genkey -validity 300")
                .shouldHaveExitValue(0);

        SecurityTools.jarsigner("-keystore ks -storepass changeit "
                + "-signedjar " + signedJar + " em.jar a")
                .shouldHaveExitValue(0);
    }
}
