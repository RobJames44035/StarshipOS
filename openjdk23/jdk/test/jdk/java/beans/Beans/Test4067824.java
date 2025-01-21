/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4067824
 * @summary Tests exception details in Beans.instantiate()
 * @author Graham Hamilton
 */

import java.beans.Beans;
import java.io.FileOutputStream;
import java.io.StreamCorruptedException;

public class Test4067824 {
    public static void main(String[] args) throws Exception {
        ClassLoader cl = Test4067824.class.getClassLoader();
        try {
            Beans.instantiate(cl, "Test4067824");
        }
        catch (ClassNotFoundException exception) {
            // This is expected.  Make sure there is the right detail message:
            if (exception.toString().indexOf("IllegalAccessException") < 0)
                throw new Error("unexpected exception", exception);
        }
        FileOutputStream fout = new FileOutputStream("foo.ser");
        fout.write(new byte [] {1, 2, 3, 4, 5});
        fout.close();
        try {
            // trying to instantiate corrupt foo.ser
            Beans.instantiate(cl, "foo");
            throw new Error("Instantiated corrupt .ser file OK!!??");
        }
        catch (ClassNotFoundException exception) {
            // expected exception
        }
        catch (StreamCorruptedException exception) {
            // expected exception
        }
    }

    // private constructor means Beans.instantiate() will fail
    private Test4067824() {
    }
}
