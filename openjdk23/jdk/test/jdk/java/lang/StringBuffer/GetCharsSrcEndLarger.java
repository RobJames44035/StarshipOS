/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4096341
   @summary StringBuffer.getChars(): JLS requires exception if
            srcBegin > srcEnd
   @author Anand Palaniswamy
 */
public class GetCharsSrcEndLarger {
    public static void main(String[] args) throws Exception {
        boolean exceptionOccurred = false;
        try {
            new StringBuffer("abc").getChars(1, 0, new char[10], 0);
        } catch (StringIndexOutOfBoundsException sioobe) {
            exceptionOccurred = true;
        }
        if (!exceptionOccurred) {
            throw new Exception("StringBuffer.getChars() must throw" +
                                " an exception if srcBegin > srcEnd");
        }
    }
}
