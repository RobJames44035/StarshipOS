/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 4034428
 * @summary Test for leading space in values output from properties
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class tests to see if the properties object saves
 * leading space in the value of a property as it should
 * do according to the JLS
 */
public class Save {

    public static void main(String argv[]) {
        int testSucceeded=0;
        FileOutputStream myOutput;

        // create a properties object to save
        Properties myProperties = new Properties();
        String value = "   spacesfirst";
        myProperties.put("atest", value);

        try {
            // save the object and check output
            myOutput = new FileOutputStream("testout");
            myProperties.store(myOutput,"A test");
            myOutput.close();

            //load the properties set
            FileInputStream myIn = new FileInputStream("testout");
            Properties myNewProps = new Properties();
            try {
                myNewProps.load(myIn);
            } finally {
                myIn.close();
            }

            //check the results
            String newValue = (String) myNewProps.get("atest");
            if (!newValue.equals(value))
                throw new RuntimeException(
                    "Properties does not save leading spaces in values correctly.");
         } catch (IOException e) { //do nothing
         }
     }
}
