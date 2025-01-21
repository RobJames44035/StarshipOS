/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Test SourceCodeAnalysis
 * @modules jdk.compiler/com.sun.tools.javac.api
 * @build KullaTesting TestingInputStream KullaCompletenessStressTest CompletenessStressTest
 * @run testng KullaCompletenessStressTest
 */

import java.io.File;

import org.testng.annotations.Test;

@Test
public class KullaCompletenessStressTest extends CompletenessStressTest {
    @Override
    public File[] getDirectoriesToTest() {
        String src = System.getProperty("test.src");
        File file;
        if (src == null) {
            file = new File("../src/jdk.jshell/share/classes");
        } else {
            file = new File(src, "../../../src/jdk.jshell/share/classes");
        }
        if (!file.exists()) {
            System.out.println("jdk.jshell sources are not exist. Test has been skipped. Path: " + file.toString());
            return new File[]{};
        }else {
            return new File[]{file};
        }
    }
}
