/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8024497
 * @summary crash returning this-referencing lambda from default method
 * @compile CrashUsingReturningThisRefLambdaFromDefaultMetTest.java
 */

interface SuperInterface {}

interface CrashUsingReturningThisRefLambdaFromDefaultMetTest extends SuperInterface {
    default Runnable getAction() {
        return () -> {
            SuperInterface.super.getClass();
            this.getClass();
            CrashUsingReturningThisRefLambdaFromDefaultMetTest.this.getClass();
        };
    }
}
