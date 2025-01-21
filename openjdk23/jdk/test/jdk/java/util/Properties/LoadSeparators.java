/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 4192678
 * @summary Test loading of values that are key value separators
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class tests to see if a properties object can successfully save and
 * load properties with a non-escaped value that is also a key value separator
 *
 */
public class LoadSeparators {
    public static void main(String[] argv) throws Exception {
        try {
            // Destroy old test file if any
            // Create a properties file
            File propFile = new File("testout");
            propFile.delete();

            // Create a properties file
            FileOutputStream myOut = new FileOutputStream(propFile);
            String testProperty = "Test3==";
            myOut.write(testProperty.getBytes());
            myOut.close();

            // Load the properties set
            FileInputStream myIn = new FileInputStream("testout");
            Properties myNewProps = new Properties();
            try {
                myNewProps.load(myIn);
            } finally {
                myIn.close();
            }

            // Read in the test property
            String equalSign = myNewProps.getProperty("Test3");

            // Clean up
            propFile.delete();

            if (!equalSign.equals("="))
                throw new Exception("Cannot read key-value separators.");
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
