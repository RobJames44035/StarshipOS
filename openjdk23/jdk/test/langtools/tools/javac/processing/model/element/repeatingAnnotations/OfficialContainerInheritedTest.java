/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug     8004822 8163113
 * @author  mnunez
 * @summary Language model api test basics for repeating annotations
 * @library /tools/javac/lib
 * @library supportingAnnotations
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor ElementRepAnnoTester
 * @compile -processor ElementRepAnnoTester -proc:only OfficialContainerInheritedTest.java
 */

@BarInheritedContainer({@BarInherited(1), @BarInherited(2)})
class D {}

@ExpectedBase(
        value = BarInherited.class,
        getAnnotation = "null",
        getAnnotationsByType = {
            "@BarInherited(1)",
            "@BarInherited(2)"
        },
        getAllAnnotationMirrors = {
            "@BarInheritedContainer({@BarInherited(1), @BarInherited(2)})",
            "ExpectedBase",
            "ExpectedContainer"
        },
        getAnnotationMirrors = {
            "ExpectedBase",
            "ExpectedContainer"
        })
@ExpectedContainer(
        value = BarInheritedContainer.class,
        getAnnotation = "@BarInheritedContainer("
        + "{@BarInherited(1), @BarInherited(2)})",
        getAnnotationsByType = {"@BarInheritedContainer("
                + "{@BarInherited(1), @BarInherited(2)})"})
class OfficialContainerInheritedTest extends D {}
