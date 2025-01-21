/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package com.sun.tools.javap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.classfile.Attributes;
import java.lang.classfile.ClassFile;

import java.lang.classfile.Instruction;
import java.lang.classfile.attribute.CodeAttribute;
import java.lang.classfile.attribute.StackMapFrameInfo;
import java.lang.classfile.attribute.StackMapTableAttribute;

/**
 * Annotate instructions with stack map.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class StackMapWriter extends InstructionDetailWriter {
    static StackMapWriter instance(Context context) {
        StackMapWriter instance = context.get(StackMapWriter.class);
        if (instance == null)
            instance = new StackMapWriter(context);
        return instance;
    }

    protected StackMapWriter(Context context) {
        super(context);
        context.put(StackMapWriter.class, this);
    }

    public void reset(CodeAttribute code) {
        setStackMap(code);
    }

    void setStackMap(CodeAttribute code) {
        StackMapTableAttribute attr = code.findAttribute(Attributes.stackMapTable())
                .orElse(null);
        if (attr == null) {
            map = null;
            return;
        }
        var m = code.parent().get();
        if ((m.flags().flagsMask() & ClassFile.ACC_STATIC) == 0) {
            thisClassName =  m.parent().get().thisClass().asInternalName();
        } else {
            thisClassName = null;
        }

        map = new HashMap<>();
        this.code = code;
        for (var fr : attr.entries())
            map.put(code.labelToBci(fr.target()), fr);
    }

    public void writeInitialDetails() {
        writeDetails(-1);
    }

    @Override
    public void writeDetails(int pc, Instruction instr) {
        writeDetails(pc);
    }

    private void writeDetails(int pc) {
        if (map == null)
            return;

        var m = map.get(pc);
        if (m != null) {
            print("StackMap locals: ", m.locals(), true);
            print("StackMap stack: ", m.stack(), false);
        }

    }

    void print(String label, List<StackMapFrameInfo.VerificationTypeInfo> entries,
            boolean firstThis) {
        print(label);
        for (var e : entries) {
            print(" ");
            print(e, firstThis);
            firstThis = false;
        }
        println();
    }

    void print(StackMapFrameInfo.VerificationTypeInfo entry, boolean firstThis) {
        if (entry == null) {
            print("ERROR");
            return;
        }

        switch (entry) {
            case StackMapFrameInfo.SimpleVerificationTypeInfo s -> {
                switch (s) {
                    case TOP ->
                        print("top");

                    case INTEGER ->
                        print("int");

                    case FLOAT ->
                        print("float");

                    case LONG ->
                        print("long");

                    case DOUBLE ->
                        print("double");

                    case NULL ->
                        print("null");

                    case UNINITIALIZED_THIS ->
                        print("uninit_this");
                }
            }

            case StackMapFrameInfo.ObjectVerificationTypeInfo o -> {
                String cln = o.className().asInternalName();
                print(firstThis && cln.equals(thisClassName) ? "this" : cln);
            }

            case StackMapFrameInfo.UninitializedVerificationTypeInfo u ->
                print(code.labelToBci(u.newTarget()));
        }

    }

    private Map<Integer, StackMapFrameInfo> map;
    private String thisClassName;
    private CodeAttribute code;
}
