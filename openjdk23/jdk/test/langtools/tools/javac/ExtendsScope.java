/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class P {
    interface I {}
}

class T extends P implements I { // error: no I in scope
}
