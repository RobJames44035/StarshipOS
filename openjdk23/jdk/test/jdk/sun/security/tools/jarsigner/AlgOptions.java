/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 5094028 6219522
 * @summary test new jarsigner -sigalg and -digestalg options
 * @author Sean Mullan
 * @library /test/lib
 */

import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AlgOptions {
    public static void main(String[] args) throws Exception {

        // copy jar file into writeable location
        Files.copy(Path.of(System.getProperty("test.src"), "AlgOptions.jar"),
                Path.of("AlgOptionsTmp.jar"));

        // test missing signature algorithm arg
        sign("-sigalg").shouldNotHaveExitValue(0);

        // test missing digest algorithm arg
        sign("-digestalg").shouldNotHaveExitValue(0);

        // test BOGUS signature algorithm
        sign("-sigalg", "BOGUS").shouldNotHaveExitValue(0);

        // test BOGUS digest algorithm
        sign("-digestalg", "BOGUS").shouldNotHaveExitValue(0);

        // test incompatible signature algorithm
        sign("-sigalg", "SHA1withDSA").shouldNotHaveExitValue(0);

        // test compatible signature algorithm
        sign("-sigalg", "SHA512withRSA").shouldHaveExitValue(0);
        verify();

        // test non-default digest algorithm
        sign("-digestalg", "SHA-1").shouldHaveExitValue(0);
        verify();

        // test SHA-512 digest algorithm (creates long lines)
        sign("-digestalg", "SHA-512", "-sigalg", "SHA512withRSA")
                .shouldHaveExitValue(0);
        verify();
    }

    static OutputAnalyzer sign(String... options) throws Exception {
        List<String> args = new ArrayList<>();
        args.add("-keystore");
        args.add(Path.of(System.getProperty("test.src"), "JarSigning.keystore")
                .toString());
        args.add("-storepass");
        args.add("bbbbbb");
        for (String option : options) {
            args.add(option);
        }
        args.add("AlgOptionsTmp.jar");
        args.add("c");
        return SecurityTools.jarsigner(args);
    }

    static void verify() throws Exception {
        SecurityTools.jarsigner(
                "-verify", "AlgOptionsTmp.jar")
                .shouldHaveExitValue(0);
    }
}
