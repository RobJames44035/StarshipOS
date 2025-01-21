/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 7003744 6999622
 * @summary Compiler error concerning final variables
 * @author mcimadamore
 *
 * @compile T7003744a.java
 */

class T7003744a {
    final Object x;

    T7003744a() {
        {
            int inx = 0;
            for(int i = 0; i < 5; i++) { }
        }
        for(String am: new String[1]) {
            final String mode = am;
        }

        x = null;
    }
}
