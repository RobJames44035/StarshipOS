/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 5086088
 * @summary check warnings generated when overriding deprecated methods
 *
 * @compile/ref=empty       -XDrawDiagnostics -Xlint:deprecation I.java
 * @compile/ref=Test1A.out  -XDrawDiagnostics -Xlint:deprecation A.java
 * @compile/ref=Test1B.out  -XDrawDiagnostics -Xlint:deprecation B.java
 * @compile/ref=Test1B2.out -XDrawDiagnostics -Xlint:deprecation B2.java
 * @compile/ref=empty       -XDrawDiagnostics -Xlint:deprecation B3.java
 * @compile/ref=empty       -XDrawDiagnostics -Xlint:deprecation Test1.java
 */


// class should compile with no deprecation warnings
class Test1 extends B
{
}
