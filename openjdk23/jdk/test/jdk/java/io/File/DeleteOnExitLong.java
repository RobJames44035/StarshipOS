/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/* @test
   @bug  6197510
   @summary Test for deleteOnExit method on long filename
 */

import java.io.File;

public class DeleteOnExitLong  {
    public static void main (String args[]) throws Exception{
        File file = new File("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa_DeletedOnExitLong").getCanonicalFile();
        file.createNewFile();
        file.deleteOnExit();
    }
}
