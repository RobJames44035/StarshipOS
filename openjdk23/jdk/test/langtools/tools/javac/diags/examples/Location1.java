/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

// key: compiler.err.cant.resolve.location
// key: compiler.misc.location.1

class Location1 {
    Object o = null;
    { Object o2 = o.v; }
}
