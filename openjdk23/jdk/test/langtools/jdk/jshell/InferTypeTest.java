/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8131027
 * @summary Test Type Inference
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jdeps/com.sun.tools.javap
 * @build KullaTesting TestingInputStream toolbox.ToolBox Compiler
 * @run testng InferTypeTest
 */

import org.testng.annotations.Test;

@Test
public class InferTypeTest extends KullaTesting {

    public void testTypeInference() {
        assertInferredType("1", "int");
        assertEval("import java.util.*;");
        assertInferredType("new ArrayList<String>()", "ArrayList<String>");
        assertInferredType("null", "Object");
        assertInferredType("1 + ", null); //incomplete
        assertInferredType("undef", null);  //unresolvable
        assertEval("List<String> l1;");
        assertEval("List<? extends String> l2;");
        assertEval("List<? super String> l3;");
        assertInferredType("l1", "List<String>");
        assertInferredType("l2", "List<? extends String>");
        assertInferredType("l3", "List<? super String>");
        assertInferredType("l1.get(0)", "String");
        assertInferredType("l2.get(0)", "String");
        assertInferredType("l3.get(0)", "Object");
        assertInferredType("\"\" + 1", "String");
        assertEval("int i = 0;");
        assertInferredType("i++", "int");
        assertInferredType("++i", "int");
        assertInferredType("i == 0 ? l1.get(0) : l2.get(0)", "String");
        assertInferredType("", null);
        assertInferredType("void test() { }", null);
        assertInferredType("class Test { }", null);
        assertInferredType("enum Test { A; }", null);
        assertInferredType("interface Test { }", null);
        assertInferredType("@interface Test { }", null);
        assertInferredType("Object o;", null);
    }

}
