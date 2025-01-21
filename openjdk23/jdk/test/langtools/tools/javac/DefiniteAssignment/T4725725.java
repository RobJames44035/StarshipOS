/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class T4725725 {
    final int x;
    final Object o = new Object() {
            int y = x; // error: x not DA
        };
    {
        x = 12;
    }
}
