/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug     8004822
 * @author  mnunez
 * @summary Language model api test basics for repeating annotations
 * @library /tools/javac/lib
 * @library supportingAnnotations
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor ElementRepAnnoTester
 * @compile -processor ElementRepAnnoTester -proc:only SingularInheritedBTest.java
 */

@FooInherited(1)
class P {}

@ExpectedBase(
        value = FooInherited.class,
        getAnnotation = "@FooInherited(2)",
        getAnnotationsByType = {"@FooInherited(2)"},
        getAllAnnotationMirrors = {
            "@FooInherited(2)",
            "ExpectedBase",
            "ExpectedContainer"
        },
        getAnnotationMirrors = {
            "@FooInherited(2)",
            "ExpectedBase",
            "ExpectedContainer"
        })
@ExpectedContainer(
        value = UnofficialInheritedContainer.class,
        getAnnotation = "null",
        getAnnotationsByType = {})
@FooInherited(2)
class SingularInheritedBTest extends P {}
