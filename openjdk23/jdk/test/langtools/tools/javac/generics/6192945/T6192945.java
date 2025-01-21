/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6192945
 * @summary Declaration order of interdependent generic types should not matter
 * @compile T6192945.java
 */

public class T6192945<E extends D, D> {}
