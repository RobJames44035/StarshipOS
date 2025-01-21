/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import static java.io.IO.*;

public class Methods {

    public static void main(String[] args) {
        switch (args[0]) {
            case "println" -> println("hello");
            case "print" -> print("hello");
            case "input" -> readln("hello");
            default -> throw new IllegalArgumentException(args[0]);
        }
    }
}
