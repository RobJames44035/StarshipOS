/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4271588
 * @summary Vector.lastIndex(Object, int) used to let you look outside the
 *          valid range in the backing array
 */

import java.util.Vector;

public class LastIndexOf {
    public static void main(String[] args) throws Exception {
        Vector v = new Vector(10);

        try {
            int i = v.lastIndexOf(null, 5);
            throw new Exception("lastIndexOf(5/10) " + i);
        } catch (IndexOutOfBoundsException e) {
        }
    }
}
