/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * This test tests that the String.CaseInsensitiveComparator.readResolve method
 * deserializes String.CASE_INSENSITIVE_ORDER into an object which satisfies the
 * equals method.
 *
 * @test
 * @bug 5035850
 * @summary Test for String.CaseInsensitiveComparator.readResolve
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CaseInsensitiveComparator {
    public static void main(String[] args) throws Exception {
        Object result;

        try (ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(outBuffer))
        {
            out.writeObject(String.CASE_INSENSITIVE_ORDER);
            out.close();

            try (ByteArrayInputStream inBuffer = new ByteArrayInputStream(outBuffer.toByteArray());
                    ObjectInputStream in = new ObjectInputStream(inBuffer))
            {
                result = in.readObject();
            }
        }

        if (!String.CASE_INSENSITIVE_ORDER.equals(result)) {
            throw new Exception("Value restored from serial form does not equal original!");
        }

        if (!result.equals(String.CASE_INSENSITIVE_ORDER)) {
            throw new Exception("Value restored from serial form does not equal original!");
        }

        if (String.CASE_INSENSITIVE_ORDER != result) {
            throw new Exception("Value restored from serial form does not equal original!");
        }
    }
}
