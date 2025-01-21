/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4925226
 * @summary ensure IllegalArgumentException is thrown if in == out
 * @author Andreas Sterbenz
 */

import java.nio.*;

import javax.crypto.*;
import javax.crypto.spec.*;

public class InOutBuffers {

    public static void main(String[] args) throws Exception {
        Cipher c = Cipher.getInstance("RC4");
        SecretKey key = new SecretKeySpec(new byte[16], "RC4");
        c.init(Cipher.ENCRYPT_MODE, key);
        ByteBuffer b = ByteBuffer.allocate(16);
        b.putInt(0x12345678);
        try {
            c.update(b, b);
            throw new Exception("Unexpectedly completed call");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        b.flip();
        try {
            c.doFinal(b, b);
            throw new Exception("Unexpectedly completed call");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println("Passed");
    }
}
