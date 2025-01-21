/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4389747
 * @summary Collections.rotate(...) returns ArithmeticException
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RotateEmpty {

    public static void main(String[] args) throws Exception {
            List l = new ArrayList();
            Collections.rotate(l, 1);
    }
}
