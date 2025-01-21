/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4283170
 * @summary Verify that multiple variables in one inteface field declaration all have initializers.
 * @author maddox
 *
 * @run compile/fail InterfaceFieldParsing_1.java
 */

interface InterfaceFieldParsing_1 {

    int i = 10, j, k;

}
