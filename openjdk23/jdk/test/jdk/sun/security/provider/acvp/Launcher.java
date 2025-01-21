/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
import jdk.test.lib.json.JSONValue;
import jtreg.SkippedException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Provider;
import java.security.Security;

/*
 * @test
 * @bug 8342442 8345057
 * @library /test/lib
 * @modules java.base/sun.security.provider
 */

/// This test runs on `internalProjection.json`-style files generated
/// by NIST's ACVP Server. See [https://github.com/usnistgov/ACVP-Server].
///
/// The files are either put into the `data` directory or another
/// directory specified by the `test.acvp.data` test property.
/// The test walks through the directory recursively and looks for
/// file names equal to or ending with `internalProjection.json` and
/// runs tests on them.
///
/// Set the `test.acvp.alg` test property to only test the specified algorithm.
///
/// Sample files can be downloaded from
/// [https://github.com/usnistgov/ACVP-Server/tree/master/gen-val/json-files].
///
/// By default, the test uses system-preferred implementations.
/// If you want to test a specific provider, set the
/// `test.acvp.provider` test property. The provider must be
/// registered.
///
/// Tests for each algorithm must be compliant to its specification linked from
/// [https://github.com/usnistgov/ACVP?tab=readme-ov-file#supported-algorithms].
///
/// Example:
/// ```
/// jtreg -Dtest.acvp.provider=SunJCE \
///       -Dtest.acvp.alg=ML-KEM \
///       -Dtest.acvp.data=/path/to/json-files/ \
///       -jdk:/path/to/jdk Launcher.java
/// ```
public class Launcher {

    private static final String ONLY_ALG
            = System.getProperty("test.acvp.alg");

    private static final Provider PROVIDER;

    private static int count = 0;
    private static int invalidTest = 0;
    private static int unsupportedTest = 0;

    static {
        var provProp = System.getProperty("test.acvp.provider");
        if (provProp != null) {
            var p = Security.getProvider(provProp);
            if (p == null) {
                System.err.println(provProp + " is not a registered provider name");
                throw new RuntimeException(provProp + " is not a registered provider name");
            }
            PROVIDER = p;
        } else {
            PROVIDER = null;
        }
    }

    public static void main(String[] args) throws Exception {

        var testDataProp = System.getProperty("test.acvp.data");
        Path dataPath = testDataProp != null
                ? Path.of(testDataProp)
                : Path.of(System.getProperty("test.src"), "data");
        System.out.println("Data path: " + dataPath);

        if (PROVIDER != null) {
            System.out.println("Provider: " + PROVIDER.getName());
        }
        if (ONLY_ALG != null) {
            System.out.println("Algorithm: " + ONLY_ALG);
        }

        try (var stream = Files.walk(dataPath)) {
            stream.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString()
                            .endsWith("internalProjection.json"))
                    .forEach(Launcher::run);
        }

        if (count > 0) {
            System.out.println();
            System.out.println("Test completed: " + count);
            System.out.println("Invalid tests: " + invalidTest);
            System.out.println("Unsupported tests: " + unsupportedTest);
        } else {
            throw new SkippedException("No supported test found");
        }
    }

    static void run(Path test) {
        try {
            JSONValue kat;
            try {
                kat = JSONValue.parse(Files.readString(test));
            } catch (Exception e) {
                System.out.println("Warning: cannot parse " + test + ". Skipped");
                invalidTest++;
                return;
            }
            var alg = kat.get("algorithm").asString();
            if (ONLY_ALG != null && !alg.equals(ONLY_ALG)) {
                return;
            }
            System.out.println(">>> Testing " + test + "...");
            switch (alg) {
                case "ML-DSA" -> {
                    ML_DSA_Test.run(kat, PROVIDER);
                    count++;
                }
                case "ML-KEM" -> {
                    ML_KEM_Test.run(kat, PROVIDER);
                    count++;
                }
                case "SHA2-256", "SHA2-224", "SHA3-256", "SHA3-224" -> {
                    SHA_Test.run(kat, PROVIDER);
                    count++;
                }
                default -> {
                    System.out.println("Skipped unsupported algorithm: " + alg);
                    unsupportedTest++;
                }
            }
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
