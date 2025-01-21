/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package helpers;

import jdk.internal.classfile.impl.LabelContext;
import jdk.internal.classfile.impl.LabelImpl;
import java.lang.classfile.instruction.LocalVariable;
import java.lang.classfile.instruction.LocalVariableType;

import java.io.FileOutputStream;
import java.util.Collection;

public class TestUtil {

    public static void assertEmpty(Collection<?> col) {
        if (!col.isEmpty()) throw new AssertionError(col);
    }

    public static void writeClass(byte[] bytes, String fn) {
        try {
            FileOutputStream out = new FileOutputStream(fn);
            out.write(bytes);
            out.close();
        } catch (Exception ex) {
            throw new InternalError(ex);
        }
    }


    public static class ExpectedLvRecord {
        int slot;
        String desc;
        String name;
        int start;
        int length;

        ExpectedLvRecord(int slot, String name, String desc, int start, int length) {
            this.slot = slot;
            this.name = name;
            this.desc = desc;
            this.start = start;
            this.length = length;
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof LocalVariable l) {
                LabelContext ctx = ((LabelImpl) l.startScope()).labelContext();
                if (!(slot == l.slot() &&
                       desc.equals(l.type().stringValue()) &&
                       name.equals(l.name().stringValue()) &&
                        ctx.labelToBci(l.startScope()) == start &&
                        ctx.labelToBci(l.endScope()) - start == length)) throw new RuntimeException(l.slot() + " " + l.name().stringValue() + " " + l.type().stringValue() + " " + ctx.labelToBci(l.startScope()) + " " + (ctx.labelToBci(l.endScope()) - start));
                return slot == l.slot() &&
                       desc.equals(l.type().stringValue()) &&
                       name.equals(l.name().stringValue()) &&
                        ctx.labelToBci(l.startScope()) == start &&
                        ctx.labelToBci(l.endScope()) - start == length;
            }

    throw new RuntimeException(other.toString());
//            return false;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        public static ExpectedLvRecord of(int slot, String name, String desc, int start, int length) {
            return new ExpectedLvRecord(slot, name, desc, start, length);
        }
    }

    public static class ExpectedLvtRecord {
        int slot;
        String signature;
        String name;
        int start;
        int length;

        ExpectedLvtRecord(int slot, String name, String signature, int start, int length) {
            this.slot = slot;
            this.name = name;
            this.signature = signature;
            this.start = start;
            this.length = length;
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof LocalVariableType l) {
                LabelContext ctx = ((LabelImpl) l.startScope()).labelContext();
                return slot == l.slot() &&
                       signature.equals(l.signature().stringValue()) &&
                       name.equals(l.name().stringValue()) &&
                        ctx.labelToBci(l.startScope()) == start &&
                        ctx.labelToBci(l.endScope()) - start == length;
            }

            return false;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        public static ExpectedLvtRecord of(int slot, String name, String signature, int start, int length) {
            return new ExpectedLvtRecord(slot, name, signature, start, length);
        }

        public String toString() {
            return "LocalVariableType[slot=" +slot + ", name=" + name + ", sig=" + signature +", start=" + start + ", length=" + length +"]";
        }
    }
}
