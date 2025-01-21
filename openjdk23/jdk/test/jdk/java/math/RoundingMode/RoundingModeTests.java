/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4851776 4891522 4905335
 * @summary Basic tests for the RoundingMode class.
 * @author Joseph D. Darcy
 */

import java.math.RoundingMode;
import java.math.BigDecimal;

public class RoundingModeTests {
    public static void main(String [] argv) {

        // For each member of the family, make sure
        // rm == valueOf(rm.toString())

        for(RoundingMode rm: RoundingMode.values()) {
            if (rm != RoundingMode.valueOf(rm.toString())) {
                throw new RuntimeException("Bad roundtrip conversion of " +
                                           rm.toString());
            }
        }

        // Test that mapping of old integers to new values is correct
        if (RoundingMode.valueOf(BigDecimal.ROUND_CEILING) !=
                RoundingMode.CEILING) {
                throw new RuntimeException("Bad mapping for ROUND_CEILING");
        }

        if (RoundingMode.valueOf(BigDecimal.ROUND_DOWN) !=
                RoundingMode.DOWN) {
                throw new RuntimeException("Bad mapping for ROUND_DOWN");
        }

        if (RoundingMode.valueOf(BigDecimal.ROUND_FLOOR) !=
                RoundingMode.FLOOR) {
                throw new RuntimeException("Bad mapping for ROUND_FLOOR");
        }

        if (RoundingMode.valueOf(BigDecimal.ROUND_HALF_DOWN) !=
                RoundingMode.HALF_DOWN) {
                throw new RuntimeException("Bad mapping for ROUND_HALF_DOWN");
        }

        if (RoundingMode.valueOf(BigDecimal.ROUND_HALF_EVEN) !=
                RoundingMode.HALF_EVEN) {
                throw new RuntimeException("Bad mapping for ROUND_HALF_EVEN");
        }

        if (RoundingMode.valueOf(BigDecimal.ROUND_HALF_UP) !=
                RoundingMode.HALF_UP) {
                throw new RuntimeException("Bad mapping for ROUND_HALF_UP");
        }

        if (RoundingMode.valueOf(BigDecimal.ROUND_UNNECESSARY) !=
                RoundingMode.UNNECESSARY) {
                throw new RuntimeException("Bad mapping for ROUND_UNNECESARY");
        }
    }
}
