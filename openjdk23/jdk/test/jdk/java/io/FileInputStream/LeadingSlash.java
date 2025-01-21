/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4487368
 * @summary Test, if FileInputStream can handle
 *          a leading slash in file name.
 */

import java.io.*;

public class LeadingSlash {
    public static void main (String args[]) throws Exception {
        if (File.separatorChar == '\\') {       // Windows
            File file = null;
            try {
                file = File.createTempFile("bug", "4487368");
                new FileInputStream("\\" + file.getPath()).close();
                new FileOutputStream("\\" + file.getPath()).close();
            } finally {
                if (file != null)
                    file.delete();
            }
        }
    }
}
