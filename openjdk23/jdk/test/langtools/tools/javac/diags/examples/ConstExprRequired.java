/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.const.expr.req

class ConstExprRequired {
    int i = 3;

    String m(int arg) {
        switch (arg) {
            case 0: return "zero";
            case 1: return "one";
            case i: return "i";
        }
    }
}
