/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4842702 8211765
 * @summary Check that constructors throw specified exceptions
 * @author Martin Buchholz
 */

import java.util.jar.JarFile;
import java.io.File;
import java.io.IOException;

public class Constructor {
    private static void Unreached (Object o)
        throws Exception
    {
        // Should never get here
        throw new Exception ("Expected exception was not thrown");
    }

    public static void main(String[] args)
        throws Exception
    {
        try { Unreached (new JarFile ((File) null, true, JarFile.OPEN_READ)); }
        catch (NullPointerException e) {}

        try { Unreached (new JarFile ((File) null, true)); }
        catch (NullPointerException e) {}

        try { Unreached (new JarFile ((File) null)); }
        catch (NullPointerException e) {}

        try { Unreached (new JarFile ((String) null, true)); }
        catch (NullPointerException e) {}

        try { Unreached (new JarFile ((String) null)); }
        catch (NullPointerException e) {}

        try { Unreached (new JarFile ("NoSuchJar.jar")); }
        catch (IOException e) {}

        try { Unreached (new JarFile (new File ("NoSuchJar.jar"))); }
        catch (IOException e) {}

        // Test that an IOExcception is thrown when an invalid charater
        // is part of the path on Windows and Unix
        final String invalidOSPath = System.getProperty("os.name")
                .startsWith("Windows") ? "C:\\*" : "foo\u0000bar";

        try { Unreached (new JarFile (invalidOSPath)); }
        catch (IOException e) {}
    }
}
