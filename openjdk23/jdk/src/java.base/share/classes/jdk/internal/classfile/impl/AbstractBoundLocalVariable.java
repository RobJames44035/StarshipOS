/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.Label;
import java.lang.classfile.constantpool.Utf8Entry;

public class AbstractBoundLocalVariable
        extends AbstractElement implements Util.WritableLocalVariable {
    protected final CodeImpl code;
    protected final int offset;
    private Utf8Entry nameEntry;
    private Utf8Entry secondaryEntry;

    public AbstractBoundLocalVariable(CodeImpl code, int offset) {
        this.code = code;
        this.offset = offset;
    }

    protected int nameIndex() {
        return code.classReader.readU2(offset + 4);
    }

    public Utf8Entry name() {
        if (nameEntry == null)
            nameEntry = code.constantPool().entryByIndex(nameIndex(), Utf8Entry.class);
        return nameEntry;
    }

    protected int secondaryIndex() {
        return code.classReader.readU2(offset + 6);
    }

    protected Utf8Entry secondaryEntry() {
        if (secondaryEntry == null)
            secondaryEntry = code.constantPool().entryByIndex(secondaryIndex(), Utf8Entry.class);
        return secondaryEntry;
    }

    public Label startScope() {
        return code.getLabel(startPc());
    }

    public Label endScope() {
        return code.getLabel(startPc() + length());
    }

    public int startPc() {
        return code.classReader.readU2(offset);
    }

    public int length() {
        return code.classReader.readU2(offset+2);
    }

    public int slot() {
        return code.classReader.readU2(offset + 8);
    }

    @Override
    public boolean writeLocalTo(BufWriterImpl b) {
        var lc = b.labelContext();
        int startBci = lc.labelToBci(startScope());
        int endBci = lc.labelToBci(endScope());
        if (startBci == -1 || endBci == -1) {
            return false;
        }
        int length = endBci - startBci;
        b.writeU2U2(startBci, length);
        if (b.canWriteDirect(code.constantPool())) {
            b.writeU2U2(nameIndex(), secondaryIndex());
        }
        else {
            b.writeU2U2(b.cpIndex(name()), b.cpIndex(secondaryEntry()));
        }
        b.writeU2(slot());
        return true;
    }
}
