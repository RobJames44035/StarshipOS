/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;

public class Test {

    public static void main(String[] args)
         throws Exception {
        String baseDir = args[0];
        String archiveName = args[1];
        String lProperty = System.getProperty("do.iterations", "5000");
        int lRepetitions = Integer.valueOf(lProperty);
        System.out.println("Start creating copys of the archive, "
                + lRepetitions + " times");
        for (int i = 0; i < lRepetitions; i++) {
            // Copy the given jar file and add a prefix
            copyFile(baseDir, archiveName, i);
        }
        System.out.println("Start opening the archives archive, "
                + lRepetitions + " times");
        System.out.println("First URL is jar:" + Paths.get(baseDir,
                0 + archiveName).toUri() + "!/foo/Test.class");
        for (int i = 0; i < lRepetitions; i++) {
            // Create URL
            String lURLPath = "jar:" + Paths.get(baseDir, i
                    + archiveName).toUri() + "!/foo/Test.class";
            URL lURL = new URL(lURLPath);
            // Open URL Connection
            try {
                URLConnection lConnection = lURL.openConnection();
                lConnection.getInputStream();
            } catch (java.io.FileNotFoundException fnfe) {
                // Ignore this one because we expect this one
            } catch (java.util.zip.ZipException ze) {
                throw new RuntimeException("Test failed: " + ze.getMessage());
            }
      }
   }

   private static void copyFile( String pBaseDir, String pArchiveName, int pIndex) {
      try {
         java.io.File lSource = new java.io.File( pBaseDir, pArchiveName );
         java.io.File lDestination = new java.io.File( pBaseDir, pIndex + pArchiveName );
         if( !lDestination.exists() ) {
            lDestination.createNewFile();
            java.io.InputStream lInput = new java.io.FileInputStream( lSource );
            java.io.OutputStream lOutput = new java.io.FileOutputStream( lDestination );
            byte[] lBuffer = new byte[ 1024 ];
            int lLength = -1;
            while( ( lLength = lInput.read( lBuffer ) ) > 0 ) {
               lOutput.write( lBuffer, 0, lLength );
            }
            lInput.close();
            lOutput.close();
         }
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }
}
