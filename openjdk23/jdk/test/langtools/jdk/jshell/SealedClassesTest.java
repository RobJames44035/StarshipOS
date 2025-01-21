/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8246353 8273257
 * @summary Test sealed class in jshell
 * @modules jdk.jshell
 * @build KullaTesting TestingInputStream ExpectedDiagnostic
 * @run testng SealedClassesTest
 */

import javax.lang.model.SourceVersion;

import jdk.jshell.TypeDeclSnippet;
import jdk.jshell.Snippet.Status;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static jdk.jshell.Snippet.Status.VALID;

@Test
public class SealedClassesTest extends KullaTesting {

    public void testSealed() {
        TypeDeclSnippet base = classKey(
                assertEval("sealed class B permits I {}",
                           ste(MAIN_SNIPPET, Status.NONEXISTENT, Status.RECOVERABLE_NOT_DEFINED, false, null)));
        assertEval("final class I extends B {}",
                   added(VALID),
                   ste(base, Status.RECOVERABLE_NOT_DEFINED, Status.VALID, true, null));
        assertEval("new I()");
    }

    public void testInterface() {
        TypeDeclSnippet base = classKey(
                assertEval("sealed interface I permits C {}",
                           ste(MAIN_SNIPPET, Status.NONEXISTENT, Status.RECOVERABLE_NOT_DEFINED, false, null)));
        assertEval("final class C implements I {}",
                   added(VALID),
                   ste(base, Status.RECOVERABLE_NOT_DEFINED, Status.VALID, true, null));
        assertEval("new C()");
    }

    public void testNonSealed() {
        TypeDeclSnippet base = classKey(
                assertEval("sealed class B permits I {}",
                           ste(MAIN_SNIPPET, Status.NONEXISTENT, Status.RECOVERABLE_NOT_DEFINED, false, null)));
        assertEval("non-sealed class I extends B {}",
                   added(VALID),
                   ste(base, Status.RECOVERABLE_NOT_DEFINED, Status.VALID, true, null));
        assertEval("class I2 extends I {}");
        assertEval("new I2()");
    }

    public void testNonSealedInterface() {
        TypeDeclSnippet base = classKey(
                assertEval("sealed interface B permits C {}",
                           ste(MAIN_SNIPPET, Status.NONEXISTENT, Status.RECOVERABLE_NOT_DEFINED, false, null)));
        assertEval("non-sealed class C implements B {}",
                   added(VALID),
                   ste(base, Status.RECOVERABLE_NOT_DEFINED, Status.VALID, true, null));
        assertEval("class C2 extends C {}");
        assertEval("new C2()");
    }
}
