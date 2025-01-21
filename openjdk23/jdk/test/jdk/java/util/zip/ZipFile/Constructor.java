/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4842702
 * @summary Check that constructors throw specified exceptions
 * @author Martin Buchholz
 */

import java.util.zip.ZipFile;
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
        try { Unreached (new ZipFile ((File) null, ZipFile.OPEN_READ)); }
        catch (NullPointerException e) {}

        try { Unreached (new ZipFile ((File) null)); }
        catch (NullPointerException e) {}

        try { Unreached (new ZipFile ((String) null)); }
        catch (NullPointerException e) {}

        try { Unreached (new ZipFile ("NoSuchZip.zip")); }
        catch (IOException e) {}

        try { Unreached (new ZipFile (new File ("NoSuchZip.zip"))); }
        catch (IOException e) {}
    }
}
