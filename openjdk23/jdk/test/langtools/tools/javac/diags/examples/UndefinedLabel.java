/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.undef.label

class LabelInUse {
    void m() {
        int[] values = { 1, 2, 3 };
        for (int v: values) {
            if (v == 2)
                continue label;
            System.err.println(v);
        }
    }
}
