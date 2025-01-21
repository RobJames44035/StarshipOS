/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8044538
 * @summary assert hit while printing relocations for jump table entries
 *
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -Xcomp -XX:CompileCommand=compileonly,java.lang.String*::* -XX:+PrintRelocations
 *                   compiler.relocations.TestPrintRelocations
 */
/**
 * The test compiles all methods (-Xcomp) and prints their relocation
 * entries (-XX:+PrintRelocations) to make sure the printing works.
 */

package compiler.relocations;

public class TestPrintRelocations {

    static public void main(String[] args) {
    }
}
