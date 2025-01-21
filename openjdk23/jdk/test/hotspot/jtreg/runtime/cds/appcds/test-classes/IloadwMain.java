/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

public class IloadwMain {
    public static void main(String args[]) {
        int result = Iloadw.run();
        if (result != 1) {
            throw new RuntimeException(
                "Failed. Result is " + result + ", expect 1.");
        } else {
            System.out.println("Passed.");
        }
    }
}
