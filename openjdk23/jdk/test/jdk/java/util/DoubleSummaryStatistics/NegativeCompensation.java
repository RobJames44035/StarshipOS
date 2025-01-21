/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8214761
 * @run testng NegativeCompensation
 * @summary When combining two DoubleSummaryStatistics, the compensation
 *          has to be subtracted.
 */

import java.util.DoubleSummaryStatistics;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NegativeCompensation {
    static final double VAL = 1.000000001;
    static final int LOG_ITER = 21;

    @Test
    public static void testErrorComparision() {
        DoubleSummaryStatistics stat0 = new DoubleSummaryStatistics();
        DoubleSummaryStatistics stat1 = new DoubleSummaryStatistics();
        DoubleSummaryStatistics stat2 = new DoubleSummaryStatistics();

        stat1.accept(VAL);
        stat1.accept(VAL);
        stat2.accept(VAL);
        stat2.accept(VAL);
        stat2.accept(VAL);

        for (int i = 0; i < LOG_ITER; ++i) {
            stat1.combine(stat2);
            stat2.combine(stat1);
        }

        for (long i = 0, iend = stat2.getCount(); i < iend; ++i) {
            stat0.accept(VAL);
        }

        double res = 0;
        for(long i = 0, iend = stat2.getCount(); i < iend; ++i) {
            res += VAL;
        }

        double absErrN = Math.abs(res - stat2.getSum());
        double absErr = Math.abs(stat0.getSum() - stat2.getSum());
        assertTrue(absErrN >= absErr,
                "Naive sum error is not greater than or equal to Summary sum");
        assertEquals(absErr, 0.0, "Absolute error is not zero");
    }
}