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
 * @compile -processor ElementRepAnnoTester -proc:only
 * MixSingularAndUnofficialContainerInheritedB2Test.java
 */

@UnofficialInheritedContainer({@FooInherited(1), @FooInherited(2)})
@FooInherited(3)
class G {}

@ExpectedBase(
        value = FooInherited.class,
        getAnnotation = "@FooInherited(0)",
        getAnnotationsByType = {"@FooInherited(0)"},
        getAllAnnotationMirrors = {
            "@FooInherited(0)",
            "@UnofficialInheritedContainer({@FooInherited(1), @FooInherited(2)})",
            "ExpectedBase",
            "ExpectedContainer"
        },
        getAnnotationMirrors = {
            "@FooInherited(0)",
            "ExpectedBase",
            "ExpectedContainer"
        })
@ExpectedContainer(
        value = UnofficialInheritedContainer.class,
        getAnnotation = "@UnofficialInheritedContainer("
        + "{@FooInherited(1), @FooInherited(2)})",
        getAnnotationsByType = {"@UnofficialInheritedContainer("
                + "{@FooInherited(1), @FooInherited(2)})"})
@FooInherited(0)
class MixSingularAndUnofficialContainerInheritedB2Test extends G{}
