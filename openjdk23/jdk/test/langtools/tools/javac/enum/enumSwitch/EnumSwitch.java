/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5055142
 * @summary javac-generated code doesn't obey binary compatibility for enums
 * @author gafter
 *
 * @compile EnumSwitch.java
 * @compile Color2.java
 * @run main EnumSwitch
 */

enum Color {
    red, green
}

public class EnumSwitch {
    static int f(Color c) {
        switch(c) {
        case red:
            return 1;
        case green: // no resolve error
            return 2;
        default:
            return 0;
        }
    }

    public static void main(String[] args) {
        f(Color.red);
        System.out.println("test passed");
    }
}
