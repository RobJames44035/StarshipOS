/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6900037
 * @summary javac should warn if earlier -source is used and bootclasspath not set
 * @compile T6900037.java
 * @compile -source 1.8 T6900037.java
 * @compile/fail/ref=T6900037.out -XDrawDiagnostics -Werror -source 1.8 T6900037.java
 * @compile -Werror -source 1.8 -Xlint:-options T6900037.java
 */

class T6900037 { }
