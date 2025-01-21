/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.data;

/**
 *
 * @author Thomas Wuerthinger
 */
public class InputBytecode {

    private final int bci;
    private final String name;
    private final String operands;
    private final String comment;
    private InputMethod inlined;

    public InputBytecode(int bci, String name, String operands, String comment) {
        this.bci = bci;
        this.name = name;
        this.operands = operands;
        this.comment = comment;
    }

    public InputMethod getInlined() {
        return inlined;
    }

    public void setInlined(InputMethod inlined) {
        this.inlined = inlined;
    }

    public int getBci() {
        return bci;
    }

    public String getName() {
        return name;
    }

    public String getOperands() {
        return operands;
    }

    public String getComment() {
        return comment;
    }
}
