/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package com.sun.hotspot.tools.compiler;

import java.io.PrintStream;

/**
 * A compilation log event that is signalled whenever a new nmethod (a native
 * method, a compilation result) is created.
 */
public class NMethod extends BasicLogEvent {

    /**
     * The nmethod's starting address in memory.
     */
    private long address;

    /**
     * The nmethod's size in bytes.
     */
    private long size;

    /**
     * The nmethod's insts size in bytes.
     */
    private long instSize;

    /**
     * The nmethod's compilation level.
     */
    private long level;

    /**
     * The name of the compiler performing this compilation.
     */
    private String compiler;

    NMethod(double s, String i, long a, long sz) {
        super(s, i);
        address = a;
        size = sz;
    }

    public void print(PrintStream out, boolean printID) {
        // XXX Currently we do nothing
        // throw new InternalError();
    }

    public long getAddress() {
        return address;
    }

    public void setAddress(long address) {
        this.address = address;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getInstSize() {
        return instSize;
    }

    public void setInstSize(long size) {
        this.instSize = size;
    }

    /**
     * @return the level
     */
    public long getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(long level) {
        assert this.level == 0 || this.level == level;
        this.level = level;
    }

    /**
     * @return the compiler
     */
    public String getCompiler() {
        return compiler;
    }

    /**
     * @param compiler the compiler to set
     */
    public void setCompiler(String compiler) {
        this.compiler = compiler;
    }
}
