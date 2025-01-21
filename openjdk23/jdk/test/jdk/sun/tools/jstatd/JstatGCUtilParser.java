/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.util.Arrays;
import java.text.NumberFormat;

import static jdk.test.lib.Asserts.*;
import jdk.test.lib.Utils;

/**
 * The helper class for parsing following output from command 'jstat -gcutil':
 *
 *  S0     S1     E      O      M     CCS    YGC     YGCT     FGC    FGCT     CGC    CGCT       GCT
 *  0.00   0.00   0.00  52.39  97.76  92.71      4     0.286    28    28.006     2     0.086    28.378
 *
 *  It will be verified that numerical values have defined types and are reasonable,
 *  for example percentage should fit within 0-100 interval.
 */
public class JstatGCUtilParser {

    public enum GcStatisticsType {
        INTEGER, DOUBLE, PERCENTAGE, PERCENTAGE_OR_DASH, INTEGER_OR_DASH, DOUBLE_OR_DASH;
    }

    public enum GcStatistics {
        S0(GcStatisticsType.PERCENTAGE_OR_DASH),
        S1(GcStatisticsType.PERCENTAGE_OR_DASH),
        E(GcStatisticsType.PERCENTAGE_OR_DASH),
        O(GcStatisticsType.PERCENTAGE_OR_DASH),
        M(GcStatisticsType.PERCENTAGE_OR_DASH),
        CCS(GcStatisticsType.PERCENTAGE_OR_DASH),
        YGC(GcStatisticsType.INTEGER_OR_DASH),
        YGCT(GcStatisticsType.DOUBLE_OR_DASH),
        FGC(GcStatisticsType.INTEGER_OR_DASH),
        FGCT(GcStatisticsType.DOUBLE_OR_DASH),
        CGC(GcStatisticsType.INTEGER_OR_DASH),
        CGCT(GcStatisticsType.DOUBLE_OR_DASH),
        GCT(GcStatisticsType.DOUBLE);

        private final GcStatisticsType type;

        private GcStatistics(GcStatisticsType type) {
            this.type = type;
        }

        private GcStatisticsType getType() {
            return type;
        }

        public static boolean isHeadline(String... valueArray) {
            if (valueArray.length != values().length) {
                return false;
            }
            int headersCount = 0;
            for (int i = 0; i < values().length; i++) {
                if (valueArray[i].equals(values()[i].toString())) {
                    headersCount++;
                }
            }
            if (headersCount != values().length) {
                return false;
            }
            return true;
        }

        private static void verifyLength(String... valueArray) throws Exception {
            assertEquals(valueArray.length, values().length,
                    "Invalid number of data columns: " + Arrays.toString(valueArray));
        }

        public static void verify(String... valueArray) throws Exception {
            verifyLength(valueArray);
            for (int i = 0; i < values().length; i++) {
                GcStatisticsType type = values()[i].getType();
                String value = valueArray[i].trim();
                if ((type.equals(GcStatisticsType.PERCENTAGE_OR_DASH)
                     || type.equals(GcStatisticsType.INTEGER_OR_DASH)
                     || type.equals(GcStatisticsType.DOUBLE_OR_DASH))
                    && value.equals("-")) {
                    continue;
                }
                if (type.equals(GcStatisticsType.INTEGER)
                    || type.equals(GcStatisticsType.INTEGER_OR_DASH)) {
                    NumberFormat.getInstance().parse(value).intValue();
                    continue;
                }
                if (type.equals(GcStatisticsType.DOUBLE)
                    || type.equals(GcStatisticsType.DOUBLE_OR_DASH)) {
                    NumberFormat.getInstance().parse(value).doubleValue();
                    continue;
                }
                double percentage = NumberFormat.getInstance().parse(value).doubleValue();
                assertTrue(0 <= percentage && percentage <= 100,
                        "Not a percentage. value: " + value + " percentage: " + percentage);
            }
        }

    }

    private final String output;

    public JstatGCUtilParser(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

    /**
     * The function will discard any lines that come before the header line.
     * This can happen if the JVM outputs a warning message for some reason
     * before running jstat.
     */
    public void parse(int samples) throws Exception {
        boolean headlineFound = false;
        int datalineCount = 0;

        String[] lines = output.split(Utils.NEW_LINE);
        for (String line : lines) {
            line = line.replaceAll("\\s+", " ").trim();
            String[] valueArray = line.split(" ");

            if (!headlineFound) {
                headlineFound = GcStatistics.isHeadline(valueArray);
                continue;
            }

            GcStatistics.verify(valueArray);
            datalineCount++;
        }

        assertTrue(headlineFound, "No or invalid headline found, expected: " +
                Utils.NEW_LINE + Arrays.toString(GcStatistics.values()).replaceAll(",", " "));
        assertEquals(samples, datalineCount,
                "Expected " + samples + " samples, got " + datalineCount);
    }

}
