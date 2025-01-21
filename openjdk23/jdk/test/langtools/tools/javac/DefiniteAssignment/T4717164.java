/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class T4717164 {
    public static void meth() {
        try {
            try {
                throw new ClassNotFoundException();
            } catch (ClassNotFoundException e) {
                throw e;
            } finally {
                return; // discards ClassNotFoundException
            }
        } catch (ClassNotFoundException e1) { // error: unreachable
        }
    }
}
