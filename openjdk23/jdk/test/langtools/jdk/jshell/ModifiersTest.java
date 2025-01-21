/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test 8167643 8129559 8247456
 * @summary Tests for modifiers
 * @build KullaTesting TestingInputStream ExpectedDiagnostic
 * @run testng ModifiersTest
 */

import java.util.ArrayList;
import java.util.List;

import java.util.function.Consumer;
import javax.tools.Diagnostic;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class ModifiersTest extends KullaTesting {

    @DataProvider(name = "ignoredModifiers")
    public Object[][] getTestCases() {
        List<Object[]> testCases = new ArrayList<>();
        String[] ignoredModifiers = new String[] {
        };
        String[] silentlyIgnoredModifiers = new String[] {
            "public", "protected", "private", "static"
        };
        String[] before = new String[] {
            "strictfp", "abstract", "final", "@X", "@X(value=9)"
        };
        String context = "@interface X { int value() default 0; }";
        Consumer<String> eval = this::assertEval;
        Consumer<String> evalWarn = s -> assertDeclareWarn1(s, "jdk.eval.warn.illegal.modifiers");
        for (String ignoredModifier : ignoredModifiers) {
            for (ClassType classType : ClassType.values()) {
                testCases.add(new Object[] { ignoredModifier, classType, evalWarn, "", null });
            }
        }
        for (String ignoredModifier : ignoredModifiers) {
            for (String preface : before) {
                testCases.add(new Object[] { ignoredModifier, ClassType.CLASS, evalWarn, preface, context});
            }
        }
        for (String ignoredModifier : silentlyIgnoredModifiers) {
            for (ClassType classType : ClassType.values()) {
                testCases.add(new Object[] { ignoredModifier, classType, eval, "", null });
            }
        }
        for (String ignoredModifier : silentlyIgnoredModifiers) {
            for (String preface : before) {
                testCases.add(new Object[] { ignoredModifier, ClassType.CLASS, eval, preface, context});
            }
        }
        return testCases.toArray(new Object[testCases.size()][]);
    }

    @Test(dataProvider = "ignoredModifiers")
    public void ignoredModifiers(String modifier, ClassType classType,
            Consumer<String> eval, String preface, String context) {
        if (context != null) {
            assertEval(context);
        }
        String decl = String.format("%s %s %s A {}", preface, modifier, classType);
        eval.accept(decl);
        if (context != null) {
            assertNumberOfActiveClasses(2);
            assertClasses(clazz(ClassType.ANNOTATION, "X"), clazz(classType, "A"));
        } else {
            assertNumberOfActiveClasses(1);
            assertClasses(clazz(classType, "A"));
        }
        assertActiveKeys();
    }

    public void accessToStaticFieldsOfClass() {
        assertEval("class A {" +
                "int x = 14;" +
                "static int y = 18;" +
                " }");
        assertDeclareFail("A.x;",
                new ExpectedDiagnostic("compiler.err.non-static.cant.be.ref", 0, 3, 1, -1, -1, Diagnostic.Kind.ERROR));
        assertEval("A.y;", "18");
        assertEval("new A().x;", "14");
        assertEval("A.y = 88;", "88");
        assertActiveKeys();
    }

    public void accessToStaticMethodsOfClass() {
        assertEval("class A {" +
                "void x() {}" +
                "static void y() {}" +
                " }");
        assertDeclareFail("A.x();",
                new ExpectedDiagnostic("compiler.err.non-static.cant.be.ref", 0, 3, 1, -1, -1, Diagnostic.Kind.ERROR));
        assertEval("A.y();");
        assertActiveKeys();
    }

    public void accessToStaticFieldsOfInterface() {
        assertEval("interface A {" +
                "int x = 14;" +
                "static int y = 18;" +
                " }");
        assertEval("A.x;", "14");
        assertEval("A.y;", "18");

        assertDeclareFail("A.x = 18;",
                new ExpectedDiagnostic("compiler.err.cant.assign.val.to.var", 0, 3, 1, -1, -1, Diagnostic.Kind.ERROR));
        assertDeclareFail("A.y = 88;",
                new ExpectedDiagnostic("compiler.err.cant.assign.val.to.var", 0, 3, 1, -1, -1, Diagnostic.Kind.ERROR));
        assertActiveKeys();
    }

    public void accessToStaticMethodsOfInterface() {
        assertEval("interface A { static void x() {} }");
        assertEval("A.x();");
        assertActiveKeys();
    }

    public void finalMethod() {
        assertEval("class A { final void f() {} }");
        assertDeclareFail("class B extends A { void f() {} }",
                new ExpectedDiagnostic("compiler.err.override.meth", 20, 31, 25, -1, -1, Diagnostic.Kind.ERROR));
        assertActiveKeys();
    }

    //TODO: is this the right semantics?
    public void finalConstructor() {
        assertDeclareFail("class A { final A() {} }",
                new ExpectedDiagnostic("compiler.err.mod.not.allowed.here", 10, 22, 16, -1, -1, Diagnostic.Kind.ERROR));
        assertActiveKeys();
    }

    //TODO: is this the right semantics?
    public void finalDefaultMethod() {
        assertDeclareFail("interface A { final default void a() {} }",
                new ExpectedDiagnostic("compiler.err.mod.not.allowed.here", 14, 39, 33, -1, -1, Diagnostic.Kind.ERROR));
        assertActiveKeys();
    }
}
