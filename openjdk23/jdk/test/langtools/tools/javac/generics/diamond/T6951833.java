/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6951833
 *
 * @summary  latest diamond implementation generates spurious raw type warnings
 * @author mcimadamore
 * @compile -Xlint:rawtypes -Werror T6951833.java
 *
 */

class T6951833<X> {
   T6951833<String> bug = new T6951833<>();
}
