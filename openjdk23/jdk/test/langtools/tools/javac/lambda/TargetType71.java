/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8011377
 * @summary Javac crashes when multiple lambdas are defined in an array
 * @compile TargetType71.java
 */
class TargetType71 {
    void test() {
        Runnable[] rs = { () -> { String x = null; }, () -> { String x = null; } };
    }
}
