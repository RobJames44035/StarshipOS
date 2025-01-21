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
 * @compile -processor ElementRepAnnoTester -proc:only OfficialContainerBasicTest.java
 */

@ExpectedBase(
        value = Bar.class,
        getAnnotation = "null",
        getAnnotationsByType = {
            "@Bar(1)",
            "@Bar(2)"
        },
        getAllAnnotationMirrors = {
            "@BarContainer({@Bar(1), @Bar(2)})",
            "ExpectedBase",
            "ExpectedContainer"
        },
        getAnnotationMirrors = {
            "@BarContainer({@Bar(1), @Bar(2)})",
            "ExpectedBase",
            "ExpectedContainer"
        })
@ExpectedContainer(
        value = BarContainer.class,
        getAnnotation = "@BarContainer({@Bar(1), @Bar(2)})",
        getAnnotationsByType = {"@BarContainer({@Bar(1), @Bar(2)})"})
@BarContainer({@Bar(1), @Bar(2)})
class OfficialContainerBasicTest {

    @ExpectedBase(
            value = Bar.class,
            getAnnotation = "null",
            getAnnotationsByType = {
                "@Bar(1)",
                "@Bar(2)"
            },
            getAllAnnotationMirrors = {
                "@BarContainer({@Bar(1), @Bar(2)})",
                "ExpectedBase",
                "ExpectedContainer"
            },
            getAnnotationMirrors = {
                "@BarContainer({@Bar(1), @Bar(2)})",
                "ExpectedBase",
                "ExpectedContainer"
            })
    @ExpectedContainer(
            value = BarContainer.class,
            getAnnotation = "@BarContainer({@Bar(1), @Bar(2)})",
            getAnnotationsByType = {"@BarContainer({@Bar(1), @Bar(2)})"})
    @BarContainer({@Bar(1), @Bar(2)})
    int testField = 0;

    @ExpectedBase(
            value = Bar.class,
            getAnnotation = "null",
            getAnnotationsByType = {
                "@Bar(1)",
                "@Bar(2)"
            },
            getAllAnnotationMirrors = {
                "@BarContainer({@Bar(1), @Bar(2)})",
                "ExpectedBase",
                "ExpectedContainer"
            },
            getAnnotationMirrors = {
                "@BarContainer({@Bar(1), @Bar(2)})",
                "ExpectedBase",
                "ExpectedContainer"
            })
    @ExpectedContainer(
            value = BarContainer.class,
            getAnnotation = "@BarContainer({@Bar(1), @Bar(2)})",
            getAnnotationsByType = {"@BarContainer({@Bar(1), @Bar(2)})"})
    @BarContainer({@Bar(1), @Bar(2)})
    void testMethod() {}
}
