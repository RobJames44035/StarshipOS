/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/**
 * @test
 * @bug 6412896
 * @summary Make sure that an IllegalArgumentException is thrown
 *    if the length of any row in zoneStrings array is less than 5
 */

import java.text.*;

public class bug6412896 {

    static final String[][] zoneOK = {{"America/Los_Angeles", "Pacific Standard Time", "PST", "Pacific Daylight Time", "PDT"}};
    static final String[][] zoneNG = {{"America/Los_Angeles", "Pacific Standard Time", "PST", "Pacific Daylight Time"}};

    public static void main(String[] args) {

        DateFormatSymbols dfs = DateFormatSymbols.getInstance();

        dfs.setZoneStrings(zoneOK);

        try {
            dfs.setZoneStrings(zoneNG);
            throw new RuntimeException("should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }
}
