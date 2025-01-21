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
 * @compile -processor ElementRepAnnoTester -proc:only MixSingularAndUnofficialContainerBasicTest.java
 */

@ExpectedBase(
        value = Foo.class,
        getAnnotation = "@Foo(0)",
        getAnnotationsByType = {"@Foo(0)"},
        getAllAnnotationMirrors = {
            "@Foo(0)",
            "@UnofficialContainer({@Foo(1), @Foo(2)})",
            "ExpectedBase",
            "ExpectedContainer"
        },
        getAnnotationMirrors = {
            "@Foo(0)",
            "@UnofficialContainer({@Foo(1), @Foo(2)})",
            "ExpectedBase",
            "ExpectedContainer"
        })
@ExpectedContainer(
        value = UnofficialContainer.class,
        getAnnotation = "@UnofficialContainer("
        + "{@Foo(1), @Foo(2)})",
        getAnnotationsByType = {"@UnofficialContainer("
                + "{@Foo(1), @Foo(2)})"})
@Foo(0)
@UnofficialContainer({@Foo(1), @Foo(2)})
class MixSingularAndUnofficialContainerBasicTest {

    @ExpectedBase(
            value = Foo.class,
            getAnnotation = "@Foo(0)",
            getAnnotationsByType = {"@Foo(0)"},
            getAllAnnotationMirrors = {
                "@Foo(0)",
                "@UnofficialContainer({@Foo(1), @Foo(2)})",
                "ExpectedBase",
                "ExpectedContainer"
            },
            getAnnotationMirrors = {
                "@Foo(0)",
                "@UnofficialContainer({@Foo(1), @Foo(2)})",
                "ExpectedBase",
                "ExpectedContainer"
            })
    @ExpectedContainer(
            value = UnofficialContainer.class,
            getAnnotation = "@UnofficialContainer("
            + "{@Foo(1), @Foo(2)})",
            getAnnotationsByType = {"@UnofficialContainer("
                    + "{@Foo(1), @Foo(2)})"})
    @Foo(0)
    @UnofficialContainer({@Foo(1), @Foo(2)})
    int testField = 0;

    @ExpectedBase(
            value = Foo.class,
            getAnnotation = "@Foo(0)",
            getAnnotationsByType = {"@Foo(0)"},
            getAllAnnotationMirrors = {
                "@Foo(0)",
                "@UnofficialContainer({@Foo(1), @Foo(2)})",
                "ExpectedBase",
                "ExpectedContainer"
            },
            getAnnotationMirrors = {
                "@Foo(0)",
                "@UnofficialContainer({@Foo(1), @Foo(2)})",
                "ExpectedBase",
                "ExpectedContainer"
            })
    @ExpectedContainer(
            value = UnofficialContainer.class,
            getAnnotation = "@UnofficialContainer("
            + "{@Foo(1), @Foo(2)})",
            getAnnotationsByType = {"@UnofficialContainer("
                    + "{@Foo(1), @Foo(2)})"})
    @Foo(0)
    @UnofficialContainer({@Foo(1), @Foo(2)})
    void testMethod() {}
}
