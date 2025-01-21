/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import pack1.P1;

class CMain {

    class Foo {
        class Bar {}
    }
    Foo.Bar yy  = x.new Foo.Bar();      // ERROR - Type in qualified 'new' must be unqualified
}
