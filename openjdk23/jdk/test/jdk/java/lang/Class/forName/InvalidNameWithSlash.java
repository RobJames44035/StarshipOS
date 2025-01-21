/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4104861
   @summary forName is accepting methods with slashes
   @author James Bond/007
 */

public class InvalidNameWithSlash {
    public static void main(String[] args) throws Exception {
        boolean exceptionOccurred = false;
        try {
            Class c = Class.forName("java/lang.Object");
        } catch (Exception e) {
            exceptionOccurred = true;
        }
        if (!exceptionOccurred) {
            throw new Exception("forName accepting names with slashes?");
        }
    }
}
