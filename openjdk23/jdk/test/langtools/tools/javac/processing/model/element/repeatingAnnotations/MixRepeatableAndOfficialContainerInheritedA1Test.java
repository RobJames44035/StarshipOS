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
 * @compile -processor ElementRepAnnoTester -proc:only
 * MixRepeatableAndOfficialContainerInheritedA1Test.java
 */

@BarInherited(0)
class E {}

@ExpectedBase(
        value = BarInherited.class,
        getAnnotation = "@BarInherited(0)",
        getAnnotationsByType = {
            "@BarInherited(1)",
            "@BarInherited(2)"
        },
        getAllAnnotationMirrors = {
            "@BarInherited(0)",
            "@BarInheritedContainer({@BarInherited(1), @BarInherited(2)})",
            "ExpectedBase",
            "ExpectedContainer"
        },
        getAnnotationMirrors = {
            "@BarInheritedContainer({@BarInherited(1), @BarInherited(2)})",
            "ExpectedBase",
            "ExpectedContainer"
        })
@ExpectedContainer(
        value = BarInheritedContainer.class,
        getAnnotation = "@BarInheritedContainer("
        + "{@BarInherited(1), @BarInherited(2)})",
        getAnnotationsByType = {"@BarInheritedContainer("
                + "{@BarInherited(1), @BarInherited(2)})"})
@BarInheritedContainer({@BarInherited(1), @BarInherited(2)})
class MixRepeatableAndOfficialContainerInheritedA1Test extends E {}
