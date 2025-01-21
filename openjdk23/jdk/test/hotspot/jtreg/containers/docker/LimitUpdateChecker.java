/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.io.File;
import java.io.FileOutputStream;
import jdk.test.whitebox.WhiteBox;

// Check dynamic limits updating. HotSpot side.
public class LimitUpdateChecker {

    private static final File UPDATE_FILE = new File("/tmp", "limitsUpdated");
    private static final File STARTED_FILE = new File("/tmp", "started");

    public static void main(String[] args) throws Exception {
        System.out.println("LimitUpdateChecker: Entering");
        WhiteBox wb = WhiteBox.getWhiteBox();
        printMetrics(wb); // print initial limits
        createStartedFile();
        while (!UPDATE_FILE.exists()) {
            Thread.sleep(200);
        }
        System.out.println("'limitsUpdated' file appeared. Stopped loop.");
        printMetrics(wb); // print limits after update
        System.out.println("LimitUpdateChecker DONE.");

    }

    private static void printMetrics(WhiteBox wb) {
        wb.printOsInfo();
    }

    private static void createStartedFile() throws Exception {
        FileOutputStream fout = new FileOutputStream(STARTED_FILE);
        fout.close();
    }
}
