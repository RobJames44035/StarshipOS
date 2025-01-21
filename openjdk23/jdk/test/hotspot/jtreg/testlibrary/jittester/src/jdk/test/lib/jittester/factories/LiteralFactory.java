/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.Literal;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.ProductionParams;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.utils.PseudoRandom;

import java.util.Locale;

class LiteralFactory extends Factory<Literal> {
    protected final Type resultType;

    LiteralFactory(Type resultType) {
        this.resultType = resultType;
    }

    @Override
    public Literal produce() throws ProductionFailedException {
        Literal literal;
        if (resultType.equals(TypeList.BOOLEAN)) {
            literal = new Literal(PseudoRandom.randomBoolean(), TypeList.BOOLEAN);
        } else if (resultType.equals(TypeList.CHAR)) {
            literal = new Literal((char) ((char) (PseudoRandom.random() * ('z' - 'A')) + 'A'), TypeList.CHAR);
        } else if (resultType.equals(TypeList.INT)) {
            literal = new Literal((int) (PseudoRandom.random() * Integer.MAX_VALUE), TypeList.INT);
        } else if (resultType.equals(TypeList.LONG)) {
            literal = new Literal((long) (PseudoRandom.random() * Long.MAX_VALUE), TypeList.LONG);
        } else if (resultType.equals(TypeList.FLOAT)) {
            literal = new Literal(Float.valueOf(String.format(
                    (Locale) null,
                    "%." + ProductionParams.floatingPointPrecision.value() + "EF",
                    (float) PseudoRandom.random() * Float.MAX_VALUE)),
                    TypeList.FLOAT);
        } else if (resultType.equals(TypeList.DOUBLE)) {
            literal = new Literal(Double.valueOf(String.format(
                    (Locale) null,
                    "%." + 2 * ProductionParams.floatingPointPrecision.value() + "E",
                    PseudoRandom.random() * Double.MAX_VALUE)),
                    TypeList.DOUBLE);
        } else if (resultType.equals(TypeList.BYTE)) {
            literal = new Literal((byte)(PseudoRandom.random() * Byte.MAX_VALUE), TypeList.BYTE);
        } else if (resultType.equals(TypeList.SHORT)) {
            literal = new Literal((short)(PseudoRandom.random() * Short.MAX_VALUE), TypeList.SHORT);
        } else if (resultType.equals(TypeList.STRING)) {
            int size = (int) (PseudoRandom.random() * ProductionParams.stringLiteralSizeLimit.value());
            byte[] str = new byte[size];
            for (int i = 0; i < size; i++) {
                str[i] = (byte) ((int) (('z' - 'a') * PseudoRandom.random()) + 'a');
            }
            literal = new Literal(new String(str), TypeList.STRING);
        } else {
            throw new ProductionFailedException();
        }
        return literal;
    }
}
