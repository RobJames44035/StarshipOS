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
 * MixRepeatableAndOfficialContainerInheritedB2Test.java
 */

@BarInheritedContainer({@BarInherited(1), @BarInherited(2)})
@BarInherited(3)
class H {}

@ExpectedBase(
        value = BarInherited.class,
        getAnnotation = "@BarInherited(0)",
        getAnnotationsByType = {"@BarInherited(0)"},
        getAllAnnotationMirrors = {
            "@BarInherited(0)",
            "@BarInheritedContainer({@BarInherited(1), @BarInherited(2)})",
            "ExpectedBase",
            "ExpectedContainer"
        },
        getAnnotationMirrors = {
            "@BarInherited(0)",
            "ExpectedBase",
            "ExpectedContainer"
        })
@ExpectedContainer(
        value = BarInheritedContainer.class,
        getAnnotation = "@BarInheritedContainer("
        + "{@BarInherited(1), @BarInherited(2)})",
        getAnnotationsByType = {"@BarInheritedContainer("
                + "{@BarInherited(1), @BarInherited(2)})"})
@BarInherited(0)
class MixRepeatableAndOfficialContainerInheritedB2Test extends H {}
