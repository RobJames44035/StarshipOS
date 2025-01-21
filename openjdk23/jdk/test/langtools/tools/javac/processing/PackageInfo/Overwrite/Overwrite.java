/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @bug 8193037
 * @summary ensure package-infos are reset between annotation processing rounds
 * @library /tools/javac/lib
 * @modules java.compiler jdk.compiler
 * @build Processor
 * @compile package-info.java
 * @compile -processor Processor Overwrite.java
 */
package p;

public class Overwrite {}
