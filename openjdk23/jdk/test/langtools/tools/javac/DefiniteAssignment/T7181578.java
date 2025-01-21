/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7181578
 * @summary javac reports uninitialized variable with nested try...finally blocks
  *
 * @compile T7181578.java
 */
class T7181578 {
    String test(boolean cond) {
        final String s;
        try {
            if (cond) {
                try {
                    s = "";
                    return s;
                } finally { }
            } else {
                s = "";
            }
            return s; // bug occurs here: mapping is always initialized
        } finally { }
    }
}
