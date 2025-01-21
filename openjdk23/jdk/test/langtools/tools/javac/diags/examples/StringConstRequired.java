/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.string.const.req

class StringConstRequired {
    String s = "three";

    int m(String arg) {
        switch (arg) {
            case "one": return 1;
            case "two": return 2;
            case s:     return 3;
        }
    }
}
