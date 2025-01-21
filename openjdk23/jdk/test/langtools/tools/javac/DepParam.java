/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5003983 5109712
 * @summary REGRESSION: documentation comment with deprecated tag
 * @author gafter
 *
 * @compile DepParam.java
 */

class DepParam {
    void f(/** @deprecated foo. */ int foo) {
    }
}
