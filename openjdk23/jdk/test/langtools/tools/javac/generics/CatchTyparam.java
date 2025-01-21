/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class J {
    <T extends Error, U extends Error> void foo() {
        try {
            int i = 12;
        } catch (T ex) {
        } catch (U ex) {
        }
    }
}
