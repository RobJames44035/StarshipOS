/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8006582
 * @summary javac should generate method parameters correctly.
 * @build MethodParametersTester ClassFileVisitor ReflectionVisitor
 * @compile -parameters Constructors.java
 * @run main MethodParametersTester Constructors Constructors.out
 */

public class Constructors {
    public Constructors() {}
    Constructors(final Object a, final String... ba) { }
    protected Constructors(Object a, final Object ba, final String... cba) { }
    private Constructors(int a, Object ba, final Object cba, final String... dcba) { }
}



