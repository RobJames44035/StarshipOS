/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package compiler.vectorapi;

/*
 * @test TestVectorErgonomics
 * @bug 8262508
 * @requires vm.compiler2.enabled
 * @summary Check ergonomics for Vector API
 * @library /test/lib
 * @run driver compiler.vectorapi.TestVectorErgonomics
 */

import jdk.test.lib.process.ProcessTools;

public class TestVectorErgonomics {

    public static void main(String[] args) throws Throwable {
        ProcessTools.executeTestJava("--add-modules=jdk.incubator.vector", "-XX:+UnlockExperimentalVMOptions",
                                     "-XX:+EnableVectorReboxing", "-Xlog:compilation", "-version")
                    .shouldHaveExitValue(0)
                    .shouldContain("EnableVectorReboxing=true");

        ProcessTools.executeTestJava("--add-modules=jdk.incubator.vector", "-XX:+UnlockExperimentalVMOptions",
                                     "-XX:+EnableVectorAggressiveReboxing", "-Xlog:compilation", "-version")
                    .shouldHaveExitValue(0)
                    .shouldContain("EnableVectorAggressiveReboxing=true");

        ProcessTools.executeTestJava("--add-modules=jdk.incubator.vector", "-XX:+UnlockExperimentalVMOptions",
                                     "-XX:-EnableVectorReboxing", "-Xlog:compilation", "-version")
                    .shouldHaveExitValue(0)
                    .shouldContain("EnableVectorReboxing=false")
                    .shouldContain("EnableVectorAggressiveReboxing=false");

        ProcessTools.executeTestJava("--add-modules=jdk.incubator.vector", "-XX:+UnlockExperimentalVMOptions",
                                     "-XX:-EnableVectorAggressiveReboxing", "-Xlog:compilation", "-version")
                    .shouldHaveExitValue(0)
                    .shouldContain("EnableVectorAggressiveReboxing=false");

        ProcessTools.executeTestJava("--add-modules=jdk.incubator.vector", "-XX:+UnlockExperimentalVMOptions",
                                     "-XX:-EnableVectorSupport", "-Xlog:compilation", "-version")
                    .shouldHaveExitValue(0)
                    .shouldContain("EnableVectorSupport=false")
                    .shouldContain("EnableVectorReboxing=false")
                    .shouldContain("EnableVectorAggressiveReboxing=false");

        ProcessTools.executeTestJava("--add-modules=jdk.incubator.vector", "-XX:+UnlockExperimentalVMOptions",
                                     "-XX:-EnableVectorSupport", "-XX:+EnableVectorReboxing", "-Xlog:compilation", "-version")
                    .shouldHaveExitValue(0)
                    .shouldContain("EnableVectorSupport=false")
                    .shouldContain("EnableVectorReboxing=false")
                    .shouldContain("EnableVectorAggressiveReboxing=false");

        ProcessTools.executeTestJava("--add-modules=jdk.incubator.vector", "-XX:+UnlockExperimentalVMOptions",
                                     "-XX:-EnableVectorSupport", "-XX:+EnableVectorAggressiveReboxing", "-Xlog:compilation", "-version")
                    .shouldHaveExitValue(0)
                    .shouldContain("EnableVectorSupport=false")
                    .shouldContain("EnableVectorReboxing=false")
                    .shouldContain("EnableVectorAggressiveReboxing=false");
    }
}
