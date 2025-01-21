/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8153250
 * @summary Tests that files are correctly listed for a directory-relative path
 * @requires (os.family == "windows")
 */
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WinDirRelative {
    private static final char COLON = ':';
    private static final String BASENAME = "TestFile_";
    private static final String EXTENSION = ".txt";
    private static final int NUM_FILES = 10;

    private static boolean isLetter(char c) {
        return ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'));
    }

    public static void main(String[] args) throws Throwable {
        // Get the working directory which is also the default
        // directory for the current drive.
        String userDir = System.getProperty("user.dir");

        // Test only if a leading drive letter is found
        if (isLetter(userDir.charAt(0)) && userDir.charAt(1) == COLON) {
            // Create some empty files
            List<String> filenames = new ArrayList<String>(NUM_FILES);
            for (int i = 0; i < NUM_FILES; i++) {
                String filename = BASENAME + i + EXTENSION;
                filenames.add(filename);
                File f = new File(filename);
                f.createNewFile();
                f.deleteOnExit();
                System.out.printf("Created %s (%s)%n", filename,
                    f.getAbsolutePath());
            }

            // List files and verify that the ones with recognized names exist.
            String prefix = userDir.substring(0, 2);
            File p = new File(prefix);
            int failures = 0;
            int successes = 0;
            for (File f : p.listFiles()) {
                if (f.getName().toString().startsWith(BASENAME)) {
                    if (!f.exists()) {
                        System.err.printf("%s (%s) does not exist%n", f,
                            f.getAbsolutePath());
                        failures++;
                    } else {
                        successes++;
                    }
                }
            }

            // Fail if there was an existence test failure or if not
            // enough of the created files were found
            boolean testFailed = false;
            if (failures > 0) {
                System.err.println("Existence check failed");
                testFailed = true;
            }
            if (successes != NUM_FILES) {
                System.err.println("Count check failed");
                testFailed = true;
            }
            if (testFailed) {
                throw new RuntimeException("Test failed");
            }
        }
    }
}
