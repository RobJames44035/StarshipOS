/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test 1.4 00/10/18
   @bug 4231349
   @summary test runtime.exec on windows for extra space in cmd
 */
import java.io.*;

public class Space {
    public static void main(String[] args) throws Exception {
        if (File.separatorChar == '\\') {
            try {
            Process p = Runtime.getRuntime().exec( "cmd /c echo hello" );
            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(p.getInputStream()));
            p.waitFor();
            String echo = reader.readLine();
            if (echo.length() == 6)
                throw new RuntimeException("Extra space in command.");
            } catch (IOException e) {
            // not Win NT - cmd doesnt exist
            return;
            }
        }
    }
}
