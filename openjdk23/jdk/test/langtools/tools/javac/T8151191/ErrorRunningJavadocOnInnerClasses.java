/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * bug 8151191
 * @summary javac error when running javadoc on some inner classes
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build JavacTestingAbstractProcessor
 * @compile Anno.java AnnoProcessor.java ErrorRunningJavadocOnInnerClasses.java
 * @compile -processor AnnoProcessor ErrorRunningJavadocOnInnerClasses.java
 */

@Anno
public class ErrorRunningJavadocOnInnerClasses {
    ErrorRunningJavadocOnInnerClasses() {
        Runnable r = () -> {
            new Object() {};
        };
    }
}
