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
 * MixSingularAndUnofficialContainerInheritedA2Test.java
 */

@FooInherited(0)
class K {}

@ExpectedBase(
        value = FooInherited.class,
        getAnnotation = "@FooInherited(3)",
        getAnnotationsByType = {"@FooInherited(3)"},
        getAllAnnotationMirrors = {
            "@FooInherited(3)",
            "@UnofficialInheritedContainer({@FooInherited(1), @FooInherited(2)})",
            "ExpectedBase",
            "ExpectedContainer"
        },
        getAnnotationMirrors = {
            "@UnofficialInheritedContainer({@FooInherited(1), @FooInherited(2)})",
            "@FooInherited(3)",
            "ExpectedBase",
            "ExpectedContainer"
        })
@ExpectedContainer(
        value = UnofficialInheritedContainer.class,
        getAnnotation = "@UnofficialInheritedContainer("
        + "{@FooInherited(1), @FooInherited(2)})",
        getAnnotationsByType = {"@UnofficialInheritedContainer("
                + "{@FooInherited(1), @FooInherited(2)})"})
@UnofficialInheritedContainer({@FooInherited(1), @FooInherited(2)})
@FooInherited(3)
class MixSingularAndUnofficialContainerInheritedA2Test extends K {}
