/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 4218776
 * @summary Test loading of properties files with blank lines
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class tests to see if a properties object correctly handles blank
 * lines in a properties file
 */
public class BlankLines {
    public static void main(String []args)
    {
        try {
            // create test file
            File file = new File("test.properties");

            // write a single space to the test file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(' ');
            fos.close();

            // test empty properties
            Properties prop1 = new Properties();

            // now load the file we just created, into a
            // properties object.
            // the properties object should have no elements,
            // but due to a bug, it has an empty key/value.
            // key = "", value = ""
            Properties prop2 = new Properties();
            InputStream is = new FileInputStream(file);
            try {
                prop2.load(is);
            } finally {
                is.close();
            }
            if (!prop1.equals(prop2))
                throw new RuntimeException("Incorrect properties loading.");

            // cleanup
            file.delete();
        }
        catch(IOException e) {}
    }
}
