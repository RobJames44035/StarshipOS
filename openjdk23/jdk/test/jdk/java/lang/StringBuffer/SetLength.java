/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/* @test
   @bug 4089062
   @summary A String created from a StringBuffer can be overwritten
   if setLength() to a value less than the buffer length is called
   on the StringBuffer and then the StringBuffer is appended to.
   @author Robert Field
*/

public class SetLength {
    public static void main(String[] argv) throws Exception {
        StringBuffer active = new StringBuffer();
        active.append("first one");
        String a = active.toString();
        active.setLength(0);
        active.append("second");
        String b = active.toString();
        active.setLength(0);
        System.out.println("first: " + a);
        System.out.println("second: " + b);
        if (!a.equals("first one")) {
            throw new Exception("StringBuffer.setLength() overwrote string");
        }
    }
}
