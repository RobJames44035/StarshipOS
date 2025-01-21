/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6177836 8282252
 * @summary Verify BigDecimal objects with collapsed values are serialized properly.
 */

import java.math.*;
import java.io.*;
import java.util.List;

public class SerializationTests {

    public static void main(String... args) throws Exception {
        checkBigDecimalSerialRoundTrip();
        checkBigDecimalSubSerialRoundTrip();
    }

    private static void checkSerialForm(BigDecimal bd) throws Exception  {
        checkSerialForm0(bd);
        checkSerialForm0(bd.negate());
    }

    private static void checkSerialForm0(BigDecimal bd) throws Exception  {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try(ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(bd);
            oos.flush();
        }

        ObjectInputStream ois = new
            ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        BigDecimal tmp = (BigDecimal)ois.readObject();

        if (!bd.equals(tmp) ||
            bd.hashCode() != tmp.hashCode() ||
            bd.getClass() != tmp.getClass() ||
            // Directly test equality of components
            bd.scale() != tmp.scale() ||
            !bd.unscaledValue().equals(tmp.unscaledValue())) {
            System.err.print("  original : " + bd);
            System.err.println(" (hash: 0x" + Integer.toHexString(bd.hashCode()) + ")");
            System.err.print("serialized : " + tmp);
            System.err.println(" (hash: 0x" + Integer.toHexString(tmp.hashCode()) + ")");
            throw new RuntimeException("Bad serial roundtrip");
        }

        // If the class of the deserialized number is BigDecimal,
        // verify the implementation constraint on the unscaled value
        // having BigInteger class
        if (tmp.getClass() == BigDecimal.class) {
            if (tmp.unscaledValue().getClass() != BigInteger.class) {
                throw new RuntimeException("Not using genuine BigInteger as an unscaled value");
            }
        }
    }

    private static class BigIntegerSub extends BigInteger {
        public BigIntegerSub(BigInteger bi) {
            super(bi.toByteArray());
        }

        @Override
        public String toString() {
            return java.util.Arrays.toString(toByteArray());
        }
    }
    private static void checkBigDecimalSerialRoundTrip() throws Exception {
        var values =
            List.of(BigDecimal.ZERO,
                    BigDecimal.ONE,
                    BigDecimal.TEN,
                    new BigDecimal(0),
                    new BigDecimal(1),
                    new BigDecimal(10),
                    new BigDecimal(Integer.MAX_VALUE),
                    new BigDecimal(Long.MAX_VALUE-1),
                    new BigDecimal(BigInteger.valueOf(1), 1),
                    new BigDecimal(BigInteger.valueOf(100), 50),
                    new BigDecimal(new BigInteger("9223372036854775808"), // Long.MAX_VALUE + 1
                                   Integer.MAX_VALUE),
                    new BigDecimal(new BigInteger("9223372036854775808"), // Long.MAX_VALUE + 1
                                   Integer.MIN_VALUE),
                    new BigDecimal(new BigIntegerSub(BigInteger.ONE), 2));

        for(BigDecimal value : values) {
            checkSerialForm(value);
        }
    }

    private static class BigDecimalSub extends BigDecimal {
        public BigDecimalSub(BigDecimal bd) {
            super(bd.unscaledValue(), bd.scale());
        }

        @Override
        public String toString() {
            return unscaledValue() + "x10^" + (-scale());
        }
    }

    // Subclass defining a serialVersionUID
    private static class BigDecimalSubSVUID extends BigDecimal {
        @java.io.Serial
        private static long serialVesionUID = 0x0123_4567_89ab_cdefL;

        public BigDecimalSubSVUID(BigDecimal bd) {
            super(bd.unscaledValue(), bd.scale());
        }
    }

    private static void checkBigDecimalSubSerialRoundTrip() throws Exception {
        var values =
            List.of(BigDecimal.ZERO,
                    BigDecimal.ONE,
                    BigDecimal.TEN,
                    new BigDecimal(BigInteger.TEN, 1234),
                    new BigDecimal(new BigInteger("9223372036854775808"), // Long.MAX_VALUE + 1
                                   Integer.MAX_VALUE),
                    new BigDecimal(new BigInteger("9223372036854775808"), // Long.MAX_VALUE + 1
                                   Integer.MIN_VALUE));

        for(var value : values) {
            checkSerialForm(new BigDecimalSub(value));
            checkSerialForm(new BigDecimalSubSVUID(value));
        }
    }
}
