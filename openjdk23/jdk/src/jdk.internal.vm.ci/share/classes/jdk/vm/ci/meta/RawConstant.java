/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */
package jdk.vm.ci.meta;

public class RawConstant extends PrimitiveConstant {

    public RawConstant(long rawValue) {
        super(JavaKind.Int, rawValue);
    }
}
