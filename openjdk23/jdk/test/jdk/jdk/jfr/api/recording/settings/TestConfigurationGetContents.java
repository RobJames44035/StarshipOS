/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package jdk.jfr.api.recording.settings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import jdk.jfr.Configuration;
import jdk.test.lib.Asserts;

/**
 * @test
 * @summary Verifies Configuration.getContents() for every configuration
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.settings.TestConfigurationGetContents
 */
public class TestConfigurationGetContents {

    private static final String SEP = System.getProperty("file.separator");
    private static final String JFR_DIR = System.getProperty("test.jdk")
            + SEP + "lib" + SEP + "jfr" + SEP;

    public static void main(String[] args) throws Throwable {
        List<Configuration> predefinedConfigs = Configuration.getConfigurations();

        Asserts.assertNotNull(predefinedConfigs, "List of predefined configs is null");
        Asserts.assertTrue(predefinedConfigs.size() > 0, "List of predefined configs is empty");

        for (Configuration conf : predefinedConfigs) {
            String name = conf.getName();
            System.out.println("Verifying configuration " + name);
            String fpath = JFR_DIR + name + ".jfc";
            String contents = conf.getContents();
            String fileContents = readFile(fpath);
            Asserts.assertEquals(fileContents, contents, "getContents() does not return the actual contents of the file " + fpath);
        }
    }

    private static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }

}
