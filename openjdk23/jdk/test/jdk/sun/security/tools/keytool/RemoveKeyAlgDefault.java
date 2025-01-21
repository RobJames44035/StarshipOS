/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import jdk.test.lib.SecurityTools;
import jdk.test.lib.process.OutputAnalyzer;

/**
 * @test
 * @bug 8212003 8214024
 * @summary Deprecating the default keytool -keyalg option
 * @library /test/lib
 */

public class RemoveKeyAlgDefault {

    private static final String COMMON = "-keystore ks -storetype jceks "
            + "-storepass changeit -keypass changeit";

    public static void main(String[] args) throws Throwable {

        kt("-genkeypair -keyalg DSA -alias a -dname CN=A")
                .shouldHaveExitValue(0)
                .shouldContain("Generating")
                .shouldNotContain("-keyalg option must be specified");

        kt("-genkeypair -alias b -dname CN=B")
                .shouldHaveExitValue(1)
                .shouldContain("-keyalg option must be specified");

        kt("-genseckey -keyalg DES -alias c")
                .shouldHaveExitValue(0)
                .shouldContain("Generated")
                .shouldNotContain("-keyalg option must be specified");

        kt("-genseckey -alias d")
                .shouldHaveExitValue(1)
                .shouldContain("-keyalg option must be specified");
    }

    private static OutputAnalyzer kt(String cmd) throws Throwable {
        return SecurityTools.keytool(COMMON + " " + cmd);
    }
}
