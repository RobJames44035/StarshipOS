/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7043371
 * @summary javac7 fails with NPE during compilation
 * @compile T7043371.java
 */

@interface Anno {
    String value();
}

class B {
    @Anno(value=A.a)
    public static final int b = 0;
}

class A {
    @Deprecated
    public static final String a = "a";
}
