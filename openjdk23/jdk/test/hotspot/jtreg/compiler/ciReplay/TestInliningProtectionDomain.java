/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8275868
 * @library / /test/lib
 * @summary Testing that ciReplay inlining does not fail with unresolved signature classes.
 * @requires vm.flightRecorder != true & vm.compMode != "Xint" & vm.compMode != "Xcomp" & vm.debug == true & vm.compiler2.enabled
 * @modules java.base/jdk.internal.misc
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *      compiler.ciReplay.TestInliningProtectionDomain
 */

package compiler.ciReplay;

import jdk.test.lib.Asserts;

import java.util.List;

public class TestInliningProtectionDomain extends InliningBase {

    public static void main(String[] args) {
        new TestInliningProtectionDomain(ProtectionDomainTestCompiledBefore.class, true);
        new TestInliningProtectionDomain(ProtectionDomainTestNoOtherCompilationPublic.class, false);
        new TestInliningProtectionDomain(ProtectionDomainTestNoOtherCompilationPrivate.class, false);
        new TestInliningProtectionDomain(ProtectionDomainTestNoOtherCompilationPrivateString.class, false);
    }

    public TestInliningProtectionDomain(Class<?> testClass, boolean compileBar) {
        super(testClass);
        if (compileBar) {
            commandLineNormal.add("-XX:CompileCommand=compileonly," + testClass.getName() + "::bar");
        }
        runTest();
    }

    @Override
    public void testAction() {
        positiveTest(commandLineReplay);
        String entryString = getTestClass() + " " + "test";
        boolean inlineFails = testClass == ProtectionDomainTestNoOtherCompilationPrivate.class;
        int inlineeCount = inlineFails ? 1 : 5;

        List<InlineEntry> inlineesNormal = parseLogFile(LOG_FILE_NORMAL, entryString, "compile_id='" + getCompileIdFromFile(getReplayFileName()) + "'", inlineeCount);
        List<InlineEntry> inlineesReplay = parseLogFile(LOG_FILE_REPLAY, entryString, "test ()V", inlineeCount);
        verifyLists(inlineesNormal, inlineesReplay, inlineeCount);

        if (inlineFails) {
            Asserts.assertTrue(inlineesNormal.get(0).compare("compiler.ciReplay.ProtectionDomainTestNoOtherCompilationPrivate", "bar", inlineesNormal.get(0).isUnloadedSignatureClasses()));
            Asserts.assertTrue(inlineesReplay.get(0).compare("compiler.ciReplay.ProtectionDomainTestNoOtherCompilationPrivate", "bar", inlineesReplay.get(0).isDisallowedByReplay()));
        } else {
            Asserts.assertTrue(inlineesNormal.get(4).compare("compiler.ciReplay.InliningBar", "bar2", inlineesNormal.get(4).isNormalInline()));
            Asserts.assertTrue(inlineesReplay.get(4).compare("compiler.ciReplay.InliningBar", "bar2", inlineesReplay.get(4).isForcedByReplay() || inlineesReplay.get(4).isForcedIncrementalInlineByReplay()));
        }
    }
}

class ProtectionDomainTestCompiledBefore {
    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            bar(); // Ensure that bar() was compiled
        }
        for (int i = 0; i < 10000; i++) {
            test();
        }
    }

    public static void test() {
        bar();
    }

    // Integer should be resolved for the protection domain of this class because the separate compilation of bar() in
    // the normal run will resolve all classes in the signature. Inlining succeeds.
    private static Integer bar() {
        InliningFoo.foo();
        return null;
    }
}

class ProtectionDomainTestNoOtherCompilationPublic {
    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            test();
        }
    }

    public static void test() {
        bar(); // Not compiled before separately
    }

    // Integer should be resolved for the protection domain of this class because getDeclaredMethods is called in normal run
    // when validating main() method. In this process, all public methods of this class are visited and its signature classes
    // are resolved. Inlining of bar() succeeds.
    public static Integer bar() {
        InliningFoo.foo();
        return null;
    }
}

class ProtectionDomainTestNoOtherCompilationPrivate {
    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            test();
        }
    }

    public static void test() {
        bar(); // Not compiled before separately
    }

    // Integer should be unresolved for the protection domain of this class even though getDeclaredMethods is called in normal
    // run when validating main() method. In this process, only public methods of this class are visited and its signature
    // classes are resolved. Since this method is private, the signature classes are not resolved for this protection domain.
    // Inlining of bar() should fail in normal run with "unresolved signature classes". Therefore, replay compilation should
    // also not inline bar().
    private static Integer bar() {
        InliningFoo.foo();
        return null;
    }
}

class ProtectionDomainTestNoOtherCompilationPrivateString {
    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            test();
        }
    }

    public static void test() {
        bar(); // Not compiled before separately
    }

    // Integer should be resovled for the protection domain of this class because getDeclaredMethods is called in normal run
    // when validating main() method. In this process, public methods of this class are visited and its signature classes
    // are resolved. bar() is private and not visited in this process (i.e. no resolution of String). But since main()
    // has String[] as parameter, the String class will be resolved for this protection domain. Inlining of bar() succeeds.
    private static String bar() {
        InliningFoo.foo();
        return null;
    }
}

class InliningFoo {
    public static void foo() {
        foo2();
    }

    private static void foo2() {
        InliningBar.bar();
    }
}


class InliningBar {
    public static void bar() {
        bar2();
    }

    private static void bar2() {}
}
