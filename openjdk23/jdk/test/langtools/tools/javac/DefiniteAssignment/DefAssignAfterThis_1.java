/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class DefAssignAfterThis_1 {

    final int x;

    DefAssignAfterThis_1() {
        this(0);
        x = 1;          // ERROR -- duplicate assignment to blank final
    }

    DefAssignAfterThis_1(int i) {
        x = 1;
    }
}
