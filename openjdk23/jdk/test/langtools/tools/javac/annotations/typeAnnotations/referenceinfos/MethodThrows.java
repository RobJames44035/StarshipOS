/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

import static java.lang.classfile.TypeAnnotation.TargetType.*;

/*
 * @test
 * @bug 8042451
 * @summary Test population of reference info for method exception clauses
 * @compile -g Driver.java ReferenceInfoUtil.java MethodThrows.java
 * @run main Driver MethodThrows
 */
public class MethodThrows {

    @TADescription(annotation = "TA", type = THROWS, typeIndex = 0)
    @TADescription(annotation = "TB", type = THROWS, typeIndex = 2)
    public String regularMethod() {
        return "class %TEST_CLASS_NAME% { void test() throws @TA RuntimeException, IllegalArgumentException, @TB Exception { } }";
    }

    @TADescription(annotation = "TA", type = THROWS, typeIndex = 0)
    @TADescription(annotation = "TB", type = THROWS, typeIndex = 2)
    public String abstractMethod() {
        return "abstract class %TEST_CLASS_NAME% { abstract void test() throws @TA RuntimeException, IllegalArgumentException, @TB Exception; }";
    }

    @TADescription(annotation = "TA", type = THROWS, typeIndex = 0)
    @TADescription(annotation = "TB", type = THROWS, typeIndex = 2)
    public String interfaceMethod() {
        return "interface %TEST_CLASS_NAME% { void test() throws @TA RuntimeException, IllegalArgumentException, @TB Exception; }";
    }

    @TADescription(annotation = "TA", type = THROWS, typeIndex = 0,
                   genericLocation = {})
    @TADescription(annotation = "TB", type = THROWS, typeIndex = 0,
                   genericLocation = {1, 0})
    @TADescription(annotation = "TC", type = THROWS, typeIndex = 0,
                   genericLocation = {1, 0, 1, 0})
    @TADescription(annotation = "TD", type = THROWS, typeIndex = 1,
                   genericLocation = {})
    @TADescription(annotation = "TE", type = THROWS, typeIndex = 1,
                   genericLocation = {1, 0})
    @TADescription(annotation = "TF", type = THROWS, typeIndex = 1,
                   genericLocation = {1, 0, 1, 0})
    public String NestedTypes() {
        return "class Outer { class Middle { class Inner1 extends Exception {}" +
                "  class Inner2 extends Exception{} } }" +
                "class %TEST_CLASS_NAME% { void test() throws @TA Outer.@TB Middle.@TC Inner1, @TD Outer.@TE Middle.@TF Inner2 { } }";
    }
}
