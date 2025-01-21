/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug     4764778
 * @summary Mishandling interruption of I/O in Resource.getBytes
 * @author  Martin Buchholz
 */

public class InterruptedClassLoad {
    public static void main(String[] args) {
        class Empty {}
        Thread.currentThread().interrupt();
        new Empty();
    }
}
