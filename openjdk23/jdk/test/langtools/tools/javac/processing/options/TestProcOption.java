/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8308245
 * @summary Test trivial handling of -proc:full option
 * @compile -proc:full TestProcOption.java
 * @run main TestProcOption
 */

/*
 * The test verifies that compilation takes place when -proc:full is used.
 */
public class TestProcOption {
    private TestProcOption(){};

    public static void main(String... args) {
        ; // do nothing
    }
}
