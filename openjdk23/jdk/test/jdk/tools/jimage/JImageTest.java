/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
import java.io.File;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.ProviderNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import tests.Helper;
import tests.JImageGenerator;
import tests.JImageValidator;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/*
 * jimage testing.
 * @test
 * @summary Test jimage tool
 * @bug 8222100
 * @library ../lib
 * @modules java.base/jdk.internal.jimage
 *          jdk.jlink/jdk.tools.jmod
 *          jdk.jlink/jdk.tools.jimage
 *          jdk.jlink/jdk.tools.jlink.internal
 *          jdk.compiler
 * @run build JImageTest
 * @run build tests.*
 * @run main/othervm/timeout=360 -verbose:gc -Xmx1g JImageTest
*/
public class JImageTest {

    public static void main(String[] args) throws Exception {
        List<String> bootClasses = new ArrayList<>();

        FileSystem fs;
        try {
            fs = FileSystems.getFileSystem(URI.create("jrt:/"));
        } catch (ProviderNotFoundException | FileSystemNotFoundException e) {
            System.out.println("Not an image build, test skipped.");
            return;
        }

        // Build the set of locations expected in the Image
        Consumer<Path> c = (p) -> {
               // take only the .class resources.
               if (Files.isRegularFile(p) && p.toString().endsWith(".class")
                       && !p.toString().endsWith("module-info.class")) {
                   String loc = p.toString().substring("/modules".length());
                   bootClasses.add(loc);
               }
           };

        Path javabase = fs.getPath("/modules/java.base");
        Path mgtbase = fs.getPath("/modules/java.management");
        try (Stream<Path> stream = Files.walk(javabase)) {
            stream.forEach(c);
        }
        try (Stream<Path> stream = Files.walk(mgtbase)) {
            stream.forEach(c);
        }

        if (bootClasses.isEmpty()) {
            throw new RuntimeException("No boot class to check against");
        }

        File jdkHome = new File(System.getProperty("test.jdk"));
        Helper helper = Helper.newHelper();
        if (helper == null) {
            // Skip test if the jmods directory is missing (e.g. exploded image)
            System.err.println("Test not run, NO jmods directory");
            return;
        }

        // Generate the sample image
        String module = "mod1";
        String[] classes = {module + ".Main"};
        helper.generateDefaultJModule(module, Arrays.asList(classes), "java.management");

        Path image = helper.generateDefaultImage(module).assertSuccess();
        Path extractedDir = JImageGenerator.getJImageTask()
                .dir(helper.createNewExtractedDir("modules"))
                .image(image.resolve("lib").resolve("modules"))
                .extract().assertSuccess();
    }
}
