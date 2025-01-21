/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8133247
 * @summary Use of HYPOTHETICAL flag in bridge generation logic leads to missing bridges in some cases
 */

import java.lang.reflect.Method;

public class T8133247 {
    public static void main(String[] args) throws Exception {
        Method m = Class.forName("p.B").getMethod("f", Object.class);
        m.invoke(new p.B(), new Object[]{null});
    }
}
