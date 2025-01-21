/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4936392
 * @summary enum spec allows trailing comma on enum constant list
 * @author gafter
 *
 * @compile TrailingComma.java
 */

class TrailingComma {
    enum a { , };
    enum b { x , };
    enum c { , ; };
    enum d { x , ; };
}
