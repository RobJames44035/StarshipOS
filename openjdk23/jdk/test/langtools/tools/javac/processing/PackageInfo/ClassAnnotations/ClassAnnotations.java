/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @bug 8193037
 * @summary ensure annotations on package-infos loaded from the classpath are reported on all
 *     processing rounds
 * @library /tools/javac/lib
 * @modules java.compiler jdk.compiler
 * @build Processor
 * @compile package-info.java
 * @compile -processor Processor ClassAnnotations.java
 */
package p;

public class ClassAnnotations {}
