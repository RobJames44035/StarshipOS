/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/**
 * @test
 * @bug 4960776
 * @summary IvParameterSpec constructors should throw exception
 *   if the iv parameter is null.
 * @author Sean Mullan
 */
import javax.crypto.spec.IvParameterSpec;

public class NullIV {

    public static void main(String[] args) throws Exception {

        try {
            IvParameterSpec ivParams = new IvParameterSpec(null);
            throw new Exception("expected NullPointerException");
        } catch (NullPointerException npe) {}

        try {
            IvParameterSpec ivParams = new IvParameterSpec(null, 0, 0);
            throw new Exception("expected IllegalArgumentException");
        } catch (IllegalArgumentException iae) {}

    }
}
