/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Test ResourceFilter class
 * @modules jdk.jlink/jdk.tools.jlink.internal.plugins
 * @run main ResourceFilterTest
 */

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import jdk.tools.jlink.internal.plugins.ResourceFilter;

public class ResourceFilterTest {

    public static void main(String[] args) throws Exception {
        new ResourceFilterTest().test();
    }

    public void test() throws Exception {
        String[] samples = {"toto.jcov", "/module/META-INF/services/MyProvider"};
        String[] patterns = {"*.jcov", "**/META-INF/**",
                             "glob:*.jcov", "glob:**/META-INF/**",
                             "regex:.*\\.jcov", "regex:.*/META-INF/.*"};
        ResourceFilter rf = ResourceFilter.includeFilter(Arrays.asList(patterns));
        for (String s : samples) {
            if (!rf.test(s)) {
                throw new Exception("Sample " + s + "not accepted");
            }
        }
        ResourceFilter rf2 = ResourceFilter.excludeFilter(Arrays.asList(patterns));
        for (String s : samples) {
            if (rf2.test(s)) {
                throw new Exception("Sample " + s + " accepted");
            }
        }

        // Excluded resource list in a file
        File resources = new File("resources.exc");
        resources.createNewFile();
        StringBuilder builder = new StringBuilder();
        for (String p : patterns) {
            builder.append(p).append("\n");
        }
        Files.write(resources.toPath(), builder.toString().getBytes());

        String[] input = {"@" + resources.getAbsolutePath()};
        ResourceFilter rf3 = ResourceFilter.includeFilter(Arrays.asList(input));
        for (String s : samples) {
            if (!rf3.test(s)) {
                throw new Exception("Sample " + s + "not accepted");
            }
        }
        ResourceFilter rf4 = ResourceFilter.excludeFilter(Arrays.asList(input));
        for (String s : samples) {
            if (rf4.test(s)) {
                throw new Exception("Sample " + s + " accepted");
            }
        }
    }
}
