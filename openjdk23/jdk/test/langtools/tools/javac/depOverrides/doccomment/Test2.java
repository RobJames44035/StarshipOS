/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 5086088
 * @summary check warnings generated when overriding deprecated methods
 *
 * @compile/ref=Test2P.out -XDrawDiagnostics -Xlint:deprecation P.java
 * @compile/ref=Test2Q.out -XDrawDiagnostics -Xlint:deprecation Q.java
 * @compile/ref=Test2R.out -XDrawDiagnostics -Xlint:deprecation R.java
 * @compile/ref=empty   -XDrawDiagnostics -Xlint:deprecation Test2.java
 */


// class should compile with no deprecation warnings
class Test2 extends R
{
}
