/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8042451
 * @summary Test population of reference info for constructor invocation type argument
 * @compile -g Driver.java ReferenceInfoUtil.java ConstructorInvocationTypeArgument.java
 * @run main Driver ConstructorInvocationTypeArgument
 */

import static java.lang.classfile.TypeAnnotation.TargetType.CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT;

public class ConstructorInvocationTypeArgument {

    @TADescription(annotation = "TA", type = CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT,
            typeIndex = 0, offset = 4)
    @TADescription(annotation = "TB", type = CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT,
            typeIndex = 0, offset = 0)
    public String generic1() {
        return "class %TEST_CLASS_NAME% { <T> %TEST_CLASS_NAME%(int i) { new <@TA T>%TEST_CLASS_NAME%(); }" +
                " <T> %TEST_CLASS_NAME%() { <@TB String>this(0); } }";
    }

    @TADescription(annotation = "TA", type = CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT,
            typeIndex = 0, offset = 0)
    public String generic2() {
        return "class Super { <T> Super(int i) { } } " +
                "class %TEST_CLASS_NAME% extends Super { <T> %TEST_CLASS_NAME%() { <@TA String>super(0); } }";
    }

    @TADescription(annotation = "RTAs", type = CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT,
            typeIndex = 0, offset = 4)
    @TADescription(annotation = "RTBs", type = CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT,
            typeIndex = 0, offset = 0)
    public String genericRepeatableAnnotation1() {
        return "class %TEST_CLASS_NAME% { <T> %TEST_CLASS_NAME%(int i) { new <@RTA @RTA T>%TEST_CLASS_NAME%(); }" +
                " <T> %TEST_CLASS_NAME%() { <@RTB @RTB String>this(0); } }";
    }

    @TADescription(annotation = "RTAs", type = CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT,
            typeIndex = 0, offset = 0)
    public String genericRepeatableAnnotation2() {
        return "class Super { <T> Super(int i) { } } " +
                "class %TEST_CLASS_NAME% extends Super { <T> %TEST_CLASS_NAME%() { <@RTA @RTA String>super(0); } }";
    }
}
