/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package org.openjdk.tests.separate;

import java.io.*;
import java.util.*;

public class ClassToInterfaceConverter implements ClassFilePreprocessor {

    private String whichClass;

    public ClassToInterfaceConverter(String className) {
        this.whichClass = className;
    }

    private boolean utf8Matches(ClassFile.CpEntry entry, String v) {
        if (!(entry instanceof ClassFile.CpUtf8)) {
            return false;
        }
        ClassFile.CpUtf8 utf8 = (ClassFile.CpUtf8)entry;
        if (v.length() != utf8.bytes.length) {
            return false;
        }
        for (int i = 0; i < v.length(); ++i) {
            if (v.charAt(i) != utf8.bytes[i]) {
                return false;
            }
        }
        return true;
    }

    private void convertToInterface(ClassFile cf) {
        cf.access_flags = 0x0601; // ACC_INTERFACE | ACC_ABSTRACT | ACC_PUBLIC
        ArrayList<ClassFile.Method> new_methods = new ArrayList<>();
        // Find <init> method and delete it
        for (int i = 0; i < cf.methods.size(); ++i) {
            ClassFile.Method method = cf.methods.get(i);
            ClassFile.CpEntry name = cf.constant_pool.get(method.name_index);
            if (!utf8Matches(name, "<init>")) {
                new_methods.add(method);
            }
        }
        cf.methods = new_methods;
    }

    public byte[] preprocess(String classname, byte[] bytes) {
        ClassFile cf = new ClassFile(bytes);

        ClassFile.CpEntry entry = cf.constant_pool.get(cf.this_class);
        ClassFile.CpEntry name = cf.constant_pool.get(
            ((ClassFile.CpClass)entry).name_index);
        if (utf8Matches(name, whichClass)) {
            convertToInterface(cf);
            return cf.toByteArray();
        } else {
            return bytes; // unmodified
        }
    }

/*
    public static void main(String argv[]) throws Exception {
        File input = new File(argv[0]);
        byte[] buffer = new byte[(int)input.length()];
        new FileInputStream(input).read(buffer);

        ClassFilePreprocessor cfp = new ClassToInterfaceConverter("Hello");
        byte[] cf = cfp.preprocess(argv[0], buffer);
        new FileOutputStream(argv[0] + ".mod").write(cf);
    }
*/
}
