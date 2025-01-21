/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib
 * @bug 6205692
 * @summary verify MacSpi NPE on engineUpdate(ByteBuffer)
 */

import jdk.test.lib.Utils;

import javax.crypto.MacSpi;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

public class Test6205692 {

    public boolean execute() throws Exception {

        ByteBuffer byteBuffer = null;

        MyMacSpi myMacSpi = new MyMacSpi();

        Utils.runAndCheckException(() -> myMacSpi.engineUpdate(byteBuffer),
                NullPointerException.class);

        return true;
    }

    public static void main(String[] args) throws Exception {
        Test6205692 test = new Test6205692();

        if (test.execute()) {
            System.out.println(test.getClass().getName() + ": passed!");
        }
    }

    private static class MyMacSpi extends MacSpi {

        /*
         * This is the important part; the rest is blank mandatory overrides
         */
        public void engineUpdate(ByteBuffer input) {
            super.engineUpdate(input);
        }

        @Override
        protected int engineGetMacLength() {
            return 0;
        }

        @Override
        protected void engineInit(Key key, AlgorithmParameterSpec params)
                throws InvalidKeyException, InvalidAlgorithmParameterException {
        }

        @Override
        protected void engineUpdate(byte input) {
        }

        @Override
        protected void engineUpdate(byte[] input, int offset, int len) {
        }

        @Override
        protected byte[] engineDoFinal() {
            return new byte[0];
        }

        @Override
        protected void engineReset() {
        }
    }
}
