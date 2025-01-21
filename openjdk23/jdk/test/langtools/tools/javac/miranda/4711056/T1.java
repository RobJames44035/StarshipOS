/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4711056
 * @summary javac fails to detect conflicting interfaces with separate compilation
 * @author gafter
 *
 * @compile T1.java
 * @compile T2.java
 * @compile T3.java
 * @compile/fail/ref=T1.out -XDrawDiagnostics T4.java
 */

interface iclss01004_1 {
    abstract int foo(int par);
}
