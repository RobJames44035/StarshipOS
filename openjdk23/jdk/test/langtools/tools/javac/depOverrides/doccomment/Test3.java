/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

interface LibInterface {
    /** @deprecated */
        void m();
}

class LibClass {
    public void m() { }
}

class Test3 extends LibClass implements LibInterface {
}
