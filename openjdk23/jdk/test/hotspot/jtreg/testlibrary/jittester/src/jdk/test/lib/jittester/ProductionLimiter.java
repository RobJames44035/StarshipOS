/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester;

import java.time.Duration;
import java.time.Instant;

// an utility class to limit steps in the production of an expression
public class ProductionLimiter {

    private static Integer limit = -1;

    private static Instant limitInstant;

    public static void setUnlimited() {
        limit = -1;
    }

    // initialize limit state
    public static void setLimit() {
        limit = ProductionParams.productionLimit.value();
    }

    // iterate a limit, throwing exception in case it hit
    public static void limitProduction() throws ProductionFailedException {
        if (limit > 0) {
            limit--;
        }
        if (limit != -1 && limit <= 0) {
            throw new ProductionFailedException();
        }

        if (Instant.now().isAfter(limitInstant)) {
            long paramsLimitSeconds = ProductionParams.productionLimitSeconds.value();
            Duration elapsed = Duration.between(limitInstant.minusSeconds(paramsLimitSeconds), Instant.now());
            String elapsedStr = String.format("%d:%02d:%02d",
                    elapsed.toHoursPart(), elapsed.toMinutesPart(), elapsed.toSecondsPart());

            Duration timeLimit = Duration.ofSeconds(paramsLimitSeconds);
            String timeLimitStr = String.format("%d:%02d:%02d",
                            timeLimit.toHoursPart(), timeLimit.toMinutesPart(), timeLimit.toSecondsPart());

            throw new RuntimeException(String.format("A test generation took %s while limit is %s",
                        elapsedStr, timeLimitStr));
        }
    }

    public static void resetTimer() {
        limitInstant = Instant.now().plusSeconds(ProductionParams.productionLimitSeconds.value());
    }
}
