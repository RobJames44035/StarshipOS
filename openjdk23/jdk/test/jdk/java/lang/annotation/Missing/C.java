/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/**
 * Class to have a missing annotation applied for running MissingTest.
 */
public class C {
    public void method1(@Missing @Marker Object param1) {
        return;
    }
}
