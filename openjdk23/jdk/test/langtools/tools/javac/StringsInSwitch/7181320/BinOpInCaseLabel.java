/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug     7181320
 * @summary javac NullPointerException for switch labels with cast to String expressions
 * @compile BinOpInCaseLabel.java
 */

public class BinOpInCaseLabel {
    public static void main(String [] args) {
        switch (args[0]) {
            case "hello" + "world":
                break;
        }
    }
}
