/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6855671
 * @summary DerOutputStream encodes negative integer incorrectly
 * @modules java.base/sun.security.util
 */
import sun.security.util.DerOutputStream;

public class NegInt {

    public static void main(String[] args) throws Exception {
        DerOutputStream out;
        out = new DerOutputStream();
        out.putInteger(-128);
        if(out.toByteArray().length != 3) {
            throw new Exception("-128 encode error");
        }
        out = new DerOutputStream();
        out.putInteger(-129);
        if(out.toByteArray().length != 4) {
            throw new Exception("-129 encode error");
        }
    }
}
