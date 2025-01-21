/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.io.File;
import java.io.FileOutputStream;

public class WaitForFlagFile {
    public static void main(String[] args) throws Exception {
        System.out.println("WaitForFlagFile: Entering");

        File started = new File("/tmp/started");
        FileOutputStream fout = new FileOutputStream(started);
        fout.close();

        File flag = new File("/tmp/flag");
        while (!flag.exists()) {
            System.out.println("WaitForFlagFile: Waiting");
            Thread.sleep(500);
        }
        System.out.println("WaitForFlagFile: Exiting");

    }
}
