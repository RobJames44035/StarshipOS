/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/* @test
 * @bug 4082734
 * @summary Ensure that Error exception is propogated from Serializable class'
 *          readObject & writeObject method.
 */
import java.io.*;

/*
 * Failure output:
 * joef/scarry>java UserRWObjError
 * Test FAILED:
 *  java.lang.ClassCastException: java.lang.OutOfMemoryError
 *       at java.io.ObjectOutputStream.invokeObjectWriter(ObjectOutputStream.java:1379)
 *       at java.io.ObjectOutputStream.outputObject(ObjectOutputStream.java:755)
 *       at java.io.ObjectOutputStream.writeObject(Objec
 */

public class UserRWObjError implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) throws Exception {
        try {
            UserRWObjError obj = new UserRWObjError();
            ObjectOutputStream out =
                new ObjectOutputStream(new ByteArrayOutputStream());
            out.writeObject(obj);
        } catch (ClassCastException e) {
            throw e;
        } catch (OutOfMemoryError e) {
            System.err.println("Test PASSED:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An Unexpected exception occurred:");
            throw e;
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        throw new OutOfMemoryError();
    }
}
