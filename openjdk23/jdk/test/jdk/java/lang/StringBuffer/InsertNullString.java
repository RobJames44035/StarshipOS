/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4085679
   @summary JLS requires that if you insert a null string, the string
            "null" must be inserted.
   @author Anand Palaniswamy
 */
public class InsertNullString {
    public static void main(String[] args) throws Exception {
        StringBuffer s = new StringBuffer("FOOBAR");

        try {
            String nullstr = null;
            s.insert(3, nullstr); /* this will throw null pointer exception
                                  before the bug was fixed. */
            if (!s.toString().equals("FOOnullBAR")) {
                throw new Exception("StringBuffer.insert() did not insert!");
            }
        } catch (NullPointerException npe) {
            throw new Exception("StringBuffer.insert() of null String reference threw a NullPointerException");
        }
    }
}
