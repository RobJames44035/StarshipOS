/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl;

public abstract class AbstractElement {
    public AbstractElement() { }

    public void writeTo(DirectCodeBuilder builder) {
        throw new UnsupportedOperationException();
    }

    public void writeTo(DirectClassBuilder builder) {
        throw new UnsupportedOperationException();
    }

    public void writeTo(DirectMethodBuilder builder) {
        throw new UnsupportedOperationException();
    }

    public void writeTo(DirectFieldBuilder builder) {
        throw new UnsupportedOperationException();
    }
}
