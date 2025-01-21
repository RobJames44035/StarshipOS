/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class T6956758neg {

    interface I {}

    static class C {
        <T extends Object & I> T cloneObject(T object) throws Exception {
            return (T)object.clone();
        }
    }
}
