/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class T4704365 {
    T4704365() {
        switch (1) {
        case 0:
            final int i = 3; // line 1
            break;
        case i: // error: i not definitely assigned
            break;
        }
    }
}
