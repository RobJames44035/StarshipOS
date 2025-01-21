/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

class MyNonParallelLoader extends MyLoader {
    // This loader isn't parallel capable because it's not registered in the static
    // initializer as such.  parallelCapable is not an inheritable attribute.
    MyNonParallelLoader(boolean load_in_parallel) {
       super(load_in_parallel);
    }
}
