/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package com.sun.tools.jdeprscan.scan;

import java.lang.classfile.ClassModel;
import java.lang.classfile.constantpool.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * A container for selected constant pool entries. There are currently
 * lists that contain the following types of CP entries:
 *
 *  - CONSTANT_Class_info
 *  - CONSTANT_Fieldref_info
 *  - CONSTANT_Methodref_info
 *  - CONSTANT_InterfaceMethodref_info
 */
class CPEntries {
    final List<ClassEntry> classes = new ArrayList<>();
    final List<FieldRefEntry> fieldRefs = new ArrayList<>();
    final List<MethodRefEntry> methodRefs = new ArrayList<>();
    final List<InterfaceMethodRefEntry> intfMethodRefs = new ArrayList<>();

    public static CPEntries loadFrom(ClassModel cf) {
        CPEntries entries = new CPEntries();
        for (PoolEntry cpi : cf.constantPool()) {
            switch (cpi) {
                case ClassEntry ce -> entries.classes.add(ce);
                case MethodRefEntry mref -> entries.methodRefs.add(mref);
                case InterfaceMethodRefEntry imref -> entries.intfMethodRefs.add(imref);
                case FieldRefEntry fref -> entries.fieldRefs.add(fref);
                default -> {}
            }
        }
        return entries;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb, Locale.getDefault());
        f.format("Classes:%n");
        f.format("%s%n", classes);
        f.format("FieldRefs:%n");
        f.format("%s%n", fieldRefs);
        f.format("MethodRefs:%n");
        f.format("%s%n", methodRefs);
        f.format("InterfaceMethodRefs:%n");
        f.format("%s%n", intfMethodRefs);
        f.flush();
        return sb.toString();
    }
}
