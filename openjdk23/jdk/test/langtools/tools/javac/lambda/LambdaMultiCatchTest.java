/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8036942
 * @summary javac generates incorrect exception table for multi-catch statements inside a lambda
 * @run main LambdaMultiCatchTest
 */

import java.io.IOException;
import java.util.function.Function;

public class LambdaMultiCatchTest {
    public static void main(String[] args) {
        Function<String,String> fi = x -> {
            String result = "nada";
            try {
                switch (x) {
                    case "IO":  throw new IOException();
                    case "Illegal": throw new IllegalArgumentException();
                    case "Run": throw new RuntimeException();
                }
            } catch (IOException|IllegalArgumentException ex) {
               result = "IO/Illegal";
            } catch (Exception ex) {
               result = "Any";
            };
            return result;
        };
        String val = fi.apply("Run");
        if (!val.equals("Any")) {
            throw new AssertionError("Fail: Expected 'Any' but got '" + val + "'");
        }
    }
}
