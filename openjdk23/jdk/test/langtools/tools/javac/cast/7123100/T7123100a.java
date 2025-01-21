/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class T7123100a {
    <E extends Enum<E>> E m() {
        return null;
    }

    <Z> void test() {
        Z z = (Z)m();
    }
}
