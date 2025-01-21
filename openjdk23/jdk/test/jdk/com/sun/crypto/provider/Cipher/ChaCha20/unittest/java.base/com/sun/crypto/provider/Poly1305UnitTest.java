/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package com.sun.crypto.provider;

import java.nio.ByteBuffer;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

public class Poly1305UnitTest {

    public static void main(String[] args) throws Exception {
        byte[] key = new byte[] {
                 28, -110,   64,  -91,  -21,   85,  -45, -118,
                -13,   51, -120, -122,    4,  -10,  -75,  -16,
                 71,   57,   23,  -63,   64,   43, -128,    9,
                -99,  -54,   92,  -68,   32,  112,  117,  -64
        };

        Poly1305 authenticator = new Poly1305();
        if (authenticator.engineGetMacLength() != 16) {
            throw new RuntimeException(
                    "The length of Poly1305 MAC must be 16-bytes.");
        }

        authenticator.engineInit(new SecretKeySpec(key, 0, 32,
                "Poly1305"), null);

        byte[] message = new byte[] {
                 39,   84,  119,   97,  115,   32,   98,  114,
                105,  108,  108,  105,  103,   44,   32,   97,
                110,  100,   32,  116,  104,  101,   32,  115,
                108,  105,  116,  104,  121,   32,  116,  111,
                118,  101,  115,   10,   68,  105,  100,   32,
                103,  121,  114,  101,   32,   97,  110,  100,
                 32,  103,  105,  109,   98,  108,  101,   32,
                105,  110,   32,  116,  104,  101,   32,  119,
                 97,   98,  101,   58,   10,   65,  108,  108,
                 32,  109,  105,  109,  115,  121,   32,  119,
                101,  114,  101,   32,  116,  104,  101,   32,
                 98,  111,  114,  111,  103,  111,  118,  101,
                115,   44,   10,   65,  110,  100,   32,  116,
                104,  101,   32,  109,  111,  109,  101,   32,
                114,   97,  116,  104,  115,   32,  111,  117,
                116,  103,  114,   97,   98,  101,   46
        };

        authenticator.engineUpdate(
                ByteBuffer.wrap(Arrays.copyOfRange(message, 0, 8)));
        authenticator.engineUpdate(message, 8, 104);
        authenticator.engineUpdate(message, 112, 7);
        for (int i = 119; i < message.length; i++) {
            authenticator.engineUpdate(message[i]);
        }

        byte[] tag = authenticator.engineDoFinal();
        byte[] expectedTag = new byte[] {
                 69,   65,  102, -102,  126,  -86,  -18,   97,
                -25,    8,  -36,  124,  -68,  -59,  -21,   98
        };
        if (!Arrays.equals(tag, expectedTag)) {
            throw new RuntimeException(
                    "Unexpected tag: " + Arrays.toString(tag));
        }
    }
}
