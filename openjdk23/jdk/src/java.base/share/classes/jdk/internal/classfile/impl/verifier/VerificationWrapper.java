/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl.verifier;

import java.lang.classfile.Attributes;
import java.lang.classfile.ClassModel;
import java.lang.classfile.MethodModel;
import java.lang.classfile.attribute.LocalVariableInfo;
import java.lang.classfile.constantpool.ClassEntry;
import java.lang.classfile.constantpool.ConstantPool;
import java.lang.classfile.constantpool.DynamicConstantPoolEntry;
import java.lang.classfile.constantpool.MemberRefEntry;
import java.lang.classfile.constantpool.NameAndTypeEntry;
import java.lang.constant.ClassDesc;
import java.lang.reflect.AccessFlag;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.CodeImpl;
import jdk.internal.classfile.impl.Util;

public final class VerificationWrapper {
    private final ClassModel clm;
    private final ConstantPoolWrapper cp;

    public VerificationWrapper(ClassModel clm) {
        this.clm = clm;
        this.cp = new ConstantPoolWrapper(clm.constantPool());
     }

    String thisClassName() {
        return clm.thisClass().asInternalName();
    }

    int majorVersion() {
        return clm.majorVersion();
    }

    String superclassName() {
        return clm.superclass().map(ClassEntry::asInternalName).orElse(null);
    }

    Iterable<String> interfaceNames() {
        return Util.mappedList(clm.interfaces(), ClassEntry::asInternalName);
    }

    Iterable<MethodWrapper> methods() {
        return clm.methods().stream().map(m -> new MethodWrapper(m)).toList();
    }

    boolean findField(String name, String sig) {
        for (var f : clm.fields())
            if (f.fieldName().stringValue().equals(name) && f.fieldType().stringValue().equals(sig))
                return true;
        return false;
    }

    class MethodWrapper {

        final MethodModel m;
        private final CodeImpl c;
        private final List<int[]> exc;

        MethodWrapper(MethodModel m) {
            this.m = m;
            this.c = (CodeImpl)m.code().orElse(null);
            exc = new LinkedList<>();
            if (c != null) c.iterateExceptionHandlers((start, end, handler, catchType) -> {
                exc.add(new int[] {start, end, handler, catchType});
            });
        }

        ConstantPoolWrapper constantPool() {
            return cp;
        }

        boolean isNative() {
            return m.flags().has(AccessFlag.NATIVE);
        }

        boolean isAbstract() {
            return m.flags().has(AccessFlag.ABSTRACT);
        }

        boolean isBridge() {
            return m.flags().has(AccessFlag.BRIDGE);
        }

        boolean isStatic() {
            return m.flags().has(AccessFlag.STATIC);
        }

        String name() {
            return m.methodName().stringValue();
        }

        int maxStack() {
            return c == null ? 0 : c.maxStack();
        }

        int maxLocals() {
            return c == null ? 0 : c.maxLocals();
        }

        String descriptor() {
            return m.methodType().stringValue();
        }

        String parameters() {
            return m.methodTypeSymbol().parameterList().stream().map(ClassDesc::displayName).collect(Collectors.joining(","));
        }

        int codeLength() {
            return c == null ? 0 : c.codeLength();
        }

        byte[] codeArray() {
            return c == null ? null : c.codeArray();
        }

        List<int[]> exceptionTable() {
            return exc;
        }

        List<LocalVariableInfo> localVariableTable() {
            var attro = c.findAttribute(Attributes.localVariableTable());
            return attro.map(lvta -> lvta.localVariables()).orElse(List.of());
        }

        byte[] stackMapTableRawData() {
            var attro = c.findAttribute(Attributes.stackMapTable());
            return attro.map(attr -> ((BoundAttribute) attr).contents()).orElse(null);
        }

    }

    static class ConstantPoolWrapper {

        private final ConstantPool cp;

        ConstantPoolWrapper(ConstantPool cp) {
            this.cp = cp;
        }

        int entryCount() {
            return cp.size();
        }

        String classNameAt(int index) {
            return cp.entryByIndex(index, ClassEntry.class).asInternalName();
        }

        String dynamicConstantSignatureAt(int index) {
            return cp.entryByIndex(index, DynamicConstantPoolEntry.class).type().stringValue();
        }

        int tagAt(int index) {
            return cp.entryByIndex(index).tag();
        }

        private NameAndTypeEntry _refNameType(int index) {
            var e = cp.entryByIndex(index);
            return (e instanceof DynamicConstantPoolEntry de) ? de.nameAndType() :
                    e != null ? ((MemberRefEntry)e).nameAndType() : null;
        }

        String refNameAt(int index) {
            return _refNameType(index).name().stringValue();
        }

        String refSignatureAt(int index) {
            return _refNameType(index).type().stringValue();
        }

        int refClassIndexAt(int index) {
            return cp.entryByIndex(index, MemberRefEntry.class).owner().index();
        }
    }
}
