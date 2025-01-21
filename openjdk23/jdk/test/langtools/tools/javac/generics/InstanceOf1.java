/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4982096 5004321
 * @summary the type in an instanceof expression must be reifiable
 * @author seligman
 *
 * @compile  InstanceOf1.java
 */

public class InstanceOf1 {
    boolean m() {
        return this.getClass() instanceof Class<?>;
    }
}
