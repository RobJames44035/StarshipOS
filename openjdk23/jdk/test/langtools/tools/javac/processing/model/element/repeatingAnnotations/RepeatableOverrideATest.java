/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug     8004822 8007961 8163113
 * @author  mnunez
 * @summary Language model api test basics for repeating annotations
 * @library /tools/javac/lib
 * @library supportingAnnotations
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor ElementRepAnnoTester
 * @compile -processor ElementRepAnnoTester -proc:only RepeatableOverrideATest.java
 */

@BarInherited(1)
@BarInherited(2)
class B {}

@ExpectedBase(
        value = BarInherited.class,
        getAnnotation = "@BarInherited(3)",
        getAnnotationsByType = {"@BarInherited(3)"},
        getAllAnnotationMirrors = {
            "@BarInherited(3)",
            "@BarInheritedContainer({@BarInherited(1), @BarInherited(2)})",
            "ExpectedBase",
            "ExpectedContainer"
        },
        getAnnotationMirrors = {
            "@BarInherited(3)",
            "ExpectedBase",
            "ExpectedContainer"
        })
@ExpectedContainer(
        value = BarInheritedContainer.class,
        getAnnotation = "@BarInheritedContainer("
        + "{@BarInherited(1), @BarInherited(2)})",
        getAnnotationsByType = {"@BarInheritedContainer("
                + "{@BarInherited(1), @BarInherited(2)})"})
@BarInherited(3)
class RepeatableOverrideATest extends B {}
