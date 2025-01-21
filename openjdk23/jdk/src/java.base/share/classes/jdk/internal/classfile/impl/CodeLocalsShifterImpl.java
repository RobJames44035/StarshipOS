/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.CodeElement;
import java.lang.classfile.Signature;
import java.lang.classfile.TypeKind;
import jdk.internal.classfile.components.CodeLocalsShifter;
import java.lang.classfile.instruction.IncrementInstruction;
import java.lang.classfile.instruction.LoadInstruction;
import java.lang.classfile.instruction.LocalVariable;
import java.lang.classfile.instruction.LocalVariableType;
import java.lang.classfile.instruction.StoreInstruction;
import java.util.Arrays;

public final class CodeLocalsShifterImpl implements CodeLocalsShifter {

    private int[] locals = new int[0];
    private final int fixed;

    public CodeLocalsShifterImpl(int fixed) {
        this.fixed = fixed;
    }

    @Override
    public void accept(CodeBuilder cob, CodeElement coe) {
        switch (coe) {
            case LoadInstruction li ->
                cob.loadLocal(
                        li.typeKind(),
                        shift(cob, li.slot(), li.typeKind()));
            case StoreInstruction si ->
                cob.storeLocal(
                        si.typeKind(),
                        shift(cob, si.slot(), si.typeKind()));
            case IncrementInstruction ii ->
                cob.iinc(
                        shift(cob, ii.slot(), TypeKind.INT),
                        ii.constant());
            case LocalVariable lv ->
                cob.localVariable(
                        shift(cob, lv.slot(), TypeKind.fromDescriptor(lv.type().stringValue())),
                        lv.name(),
                        lv.type(),
                        lv.startScope(),
                        lv.endScope());
            case LocalVariableType lvt ->
                cob.localVariableType(
                        shift(cob, lvt.slot(),
                                (lvt.signatureSymbol() instanceof Signature.BaseTypeSig bsig)
                                        ? TypeKind.fromDescriptor(bsig.signatureString())
                                        : TypeKind.REFERENCE),
                        lvt.name(),
                        lvt.signature(),
                        lvt.startScope(),
                        lvt.endScope());
            default -> cob.with(coe);
        }
    }

    private int shift(CodeBuilder cob, int slot, TypeKind tk) {
        if (tk == TypeKind.VOID)  throw new IllegalArgumentException("Illegal local void type");
        if (slot >= fixed) {
            int key = 2*slot - fixed + tk.slotSize() - 1;
            if (key >= locals.length) locals = Arrays.copyOf(locals, key + 20);
            slot = locals[key] - 1;
            if (slot < 0) {
                slot = cob.allocateLocal(tk);
                locals[key] = slot + 1;
                if (tk.slotSize() == 2) locals[key - 1] = slot + 1;
            }
        }
        return slot;
    }
}
