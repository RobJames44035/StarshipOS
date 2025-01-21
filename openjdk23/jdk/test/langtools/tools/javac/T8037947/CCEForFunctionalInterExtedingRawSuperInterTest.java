/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8037947
 * @summary functional interface causes ClassCastException when extending raw superinterface
 * @modules jdk.compiler/com.sun.tools.javac.util
 * @compile CCEForFunctionalInterExtedingRawSuperInterTest.java
 */

public class CCEForFunctionalInterExtedingRawSuperInterTest {
    interface X<A> { <T extends A> void execute(int a); }
    interface Y<B> { <S extends B> void execute(int a); }

    @FunctionalInterface
    interface Exec<A> extends Y, X<A> { }
}
