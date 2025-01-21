/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

class Target {
    static {
        try {
            System.loadLibrary("someLibrary");
            throw new RuntimeException("someLibrary was loaded");
        } catch (UnsatisfiedLinkError e) {
            // expected: we do not have a someLibrary
        }
    }
}

