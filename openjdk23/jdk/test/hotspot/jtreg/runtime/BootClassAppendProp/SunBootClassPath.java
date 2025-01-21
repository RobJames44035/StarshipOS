/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Make sure property sun.boot.class.path is null starting with JDK-9.
 */

// Test that the value of property sun.boot.class.path is null.
public class SunBootClassPath {
    public static void main(String[] args) throws Exception {
        if (System.getProperty("sun.boot.class.path") != null) {
            throw new RuntimeException("Test failed, sun.boot.class.path has value: " +
                System.getProperty("sun.boot.class.path"));
        } else {
            System.out.println("Test SunBootClassPath passed");
        }
    }
}
