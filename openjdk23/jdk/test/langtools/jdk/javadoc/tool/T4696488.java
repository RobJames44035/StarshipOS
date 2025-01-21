/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug     4696488
 * @summary javadoc doesn't handle UNC paths for destination directory
 * @modules jdk.javadoc/jdk.javadoc.internal.doclets.toolkit:+open
 * @run main T4696488 T4696488.java
 */

import java.lang.reflect.Method;
import jdk.javadoc.internal.doclets.toolkit.BaseOptions;

public class T4696488 {

    public static void main(String... args) throws Exception {
        System.setProperty("file.separator", "/");
        assertAddTrailingFileSep("/path/to/dir", "/path/to/dir/");
        assertAddTrailingFileSep("/path/to/dir/", "/path/to/dir/");
        assertAddTrailingFileSep("/path/to/dir//", "/path/to/dir/");
        System.setProperty("file.separator", "\\");
        assertAddTrailingFileSep("C:\\path\\to\\dir", "C:\\path\\to\\dir\\");
        assertAddTrailingFileSep("C:\\path\\to\\dir\\", "C:\\path\\to\\dir\\");
        assertAddTrailingFileSep("C:\\path\\to\\dir\\\\", "C:\\path\\to\\dir\\");
        assertAddTrailingFileSep("\\\\server\\share\\path\\to\\dir", "\\\\server\\share\\path\\to\\dir\\");
        assertAddTrailingFileSep("\\\\server\\share\\path\\to\\dir\\", "\\\\server\\share\\path\\to\\dir\\");
        assertAddTrailingFileSep("\\\\server\\share\\path\\to\\dir\\\\", "\\\\server\\share\\path\\to\\dir\\");
    }

    private static void assertAddTrailingFileSep(String input, String expectedOutput) throws Exception {
        //String output = BaseOptions.addTrailingFileSep(input);
        Method m = BaseOptions.class.getDeclaredMethod("addTrailingFileSep", String.class);
        m.setAccessible(true);
        String output = (String) m.invoke(null, input);
        if (!expectedOutput.equals(output)) {
            throw new Error("expected " + expectedOutput + " but was " + output);
        }
    }
}
