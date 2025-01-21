/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4945532
 * @summary enums: test output of values().toString()
 * @author gafter
 */

import java.util.*;

public enum Enum1 {
    red, green, blue;

    public static void main(String[] args) {
        if (!Arrays.asList(values()).toString().equals("[red, green, blue]"))
            throw new Error();
    }
}
