/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8002099 6499673
 * @summary Add support for intersection types in cast expression
 * @library /tools/javac/lib
 * @modules jdk.compiler/com.sun.tools.javac.code
 *          jdk.compiler/com.sun.tools.javac.tree
 * @build JavacTestingAbstractProcessor ModelChecker
 * @compile -XDaccessInternalAPI -processor ModelChecker Model01.java
 */

import javax.lang.model.element.ElementKind;

@Check
class Test {

    interface A {
        @Member(ElementKind.METHOD)
        public void m1();
    }

    interface B {
        @Member(ElementKind.METHOD)
        public void m2();
    }

    void test(){
        @IntersectionTypeInfo({"Test.A", "Test.B"})
        Object o = (A & B)null;
    }
}
