/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8289260
 */

import java.math.BigDecimal;

public class MovePointTests {

    public static void main(String argv[]) {
        BigDecimal bd;

        bd = BigDecimal.valueOf(1, -3);
        checkNotIdentical(bd, bd.movePointLeft(0));
        checkNotIdentical(bd, bd.movePointRight(0));

        bd = BigDecimal.valueOf(1, 0);
        checkIdentical(bd, bd.movePointLeft(0));
        checkIdentical(bd, bd.movePointRight(0));

        bd = BigDecimal.valueOf(1, 3);
        checkIdentical(bd, bd.movePointLeft(0));
        checkIdentical(bd, bd.movePointRight(0));

        bd = BigDecimal.valueOf(1, -3);
        checkNotEquals(bd, bd.movePointLeft(1));
        checkNotEquals(bd, bd.movePointLeft(-1));
        checkNotEquals(bd, bd.movePointRight(1));
        checkNotEquals(bd, bd.movePointRight(-1));

        bd = BigDecimal.valueOf(1, 0);
        checkNotEquals(bd, bd.movePointLeft(1));
        checkNotEquals(bd, bd.movePointLeft(-1));
        checkNotEquals(bd, bd.movePointRight(1));
        checkNotEquals(bd, bd.movePointRight(-1));

        bd = BigDecimal.valueOf(1, 3);
        checkNotEquals(bd, bd.movePointLeft(1));
        checkNotEquals(bd, bd.movePointLeft(-1));
        checkNotEquals(bd, bd.movePointRight(1));
        checkNotEquals(bd, bd.movePointRight(-1));

        bd = BigDecimal.valueOf(1, -3);
        checkNotEquals(bd, bd.movePointLeft(10));
        checkNotEquals(bd, bd.movePointLeft(-10));
        checkNotEquals(bd, bd.movePointRight(10));
        checkNotEquals(bd, bd.movePointRight(-10));

        bd = BigDecimal.valueOf(1, 0);
        checkNotEquals(bd, bd.movePointLeft(10));
        checkNotEquals(bd, bd.movePointLeft(-10));
        checkNotEquals(bd, bd.movePointRight(10));
        checkNotEquals(bd, bd.movePointRight(-10));

        bd = BigDecimal.valueOf(1, 3);
        checkNotEquals(bd, bd.movePointLeft(10));
        checkNotEquals(bd, bd.movePointLeft(-10));
        checkNotEquals(bd, bd.movePointRight(10));
        checkNotEquals(bd, bd.movePointRight(-10));
    }

    private static void checkIdentical(BigDecimal bd, BigDecimal res) {
        if (res != bd) {  // intentionally !=
            throw new RuntimeException("Unexpected result " +
                    bd + " != " + res);
        }
    }

    private static void checkNotIdentical(BigDecimal bd, BigDecimal res) {
        if (res == bd) {  // intentionally ==
            throw new RuntimeException("Unexpected result " +
                    bd + " == " + res);
        }
    }

    private static void checkNotEquals(BigDecimal bd, BigDecimal res) {
        if (res.equals(bd)) {
            throw new RuntimeException("Unexpected result " +
                    bd + ".equals(" + res + ")");
        }
    }

}
