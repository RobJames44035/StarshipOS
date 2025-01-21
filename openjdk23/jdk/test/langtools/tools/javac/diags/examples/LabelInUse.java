/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.label.already.in.use

class LabelInUse {
    void m(String... args) {
        label:
        for (String a1: args) {
            label:
            for (String a2: args) {
                System.out.println(a1 + " " + a2);
            }
        }
    }
}

