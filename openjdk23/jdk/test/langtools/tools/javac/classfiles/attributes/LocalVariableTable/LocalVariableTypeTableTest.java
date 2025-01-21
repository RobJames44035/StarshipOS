/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @summary local variable type table attribute test.
 * @bug 8040097
 * @library /tools/lib /tools/javac/lib ../lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 *          java.base/jdk.internal.classfile.impl
 * @build toolbox.ToolBox InMemoryFileManager TestBase LocalVariableTestBase
 * @compile -g LocalVariableTypeTableTest.java
 * @run main LocalVariableTypeTableTest
 */

import java.lang.classfile.attribute.*;
import jdk.internal.classfile.impl.BoundAttribute;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class LocalVariableTypeTableTest<THIS> extends LocalVariableTestBase {

    public LocalVariableTypeTableTest(Class<?> clazz) {
        super(clazz);
    }

    public static void main(String[] args) throws IOException {
        new LocalVariableTypeTableTest(LocalVariableTypeTableTest.class).test();
    }

    @Override
    protected List<VariableTable> getVariableTables(CodeAttribute codeAttribute) {
        return codeAttribute.attributes().stream()
                .filter(at -> at instanceof LocalVariableTypeTableAttribute)
                .map(at -> (LocalVariableTypeTableAttribute) at)
                .map(LocalVariableTypeTable::new).collect(toList());
    }

    @ExpectedLocals(name = "list", type = "TT;")
    @ExpectedLocals(name = "p", type = "[TP;")
    @ExpectedLocals(name = "k", type = "TK;")
    @ExpectedLocals(name = "c1", type = "Ljava/util/Collection<-Ljava/lang/Integer;>;")
    @ExpectedLocals(name = "c2", type = "Ljava/util/Collection<*>;")
    @ExpectedLocals(name = "c3", type = "Ljava/util/Collection<+TE;>;")
    public <T extends List<Integer>, P, K extends Integer, E extends Supplier & Runnable>
    void genericTypeWithParametersOnly(K k, T list, P[] p,
                                       Collection<? super Integer> c1,
                                       Collection<?> c2, Collection<? extends E> c3) {
    }

    @ExpectedLocals(name = "list", type = "TT;")
    @ExpectedLocals(name = "p", type = "[TP;")
    @ExpectedLocals(name = "k", type = "TK;")
    @ExpectedLocals(name = "c1", type = "Ljava/util/Collection<-Ljava/lang/Integer;>;")
    @ExpectedLocals(name = "c2", type = "Ljava/util/Collection<*>;")
    @ExpectedLocals(name = "c3", type = "Ljava/util/Collection<+TE;>;")
    public <T extends List<Integer>, P, K extends Integer, E extends Supplier & Runnable>
    void genericType(K k, T list, P[] p) {
        Collection<? super Integer> c1 = null;
        Collection<?> c2 = null;
        Collection<? extends E> c3 = null;
    }

    @ExpectedLocals(name = "list", type = "TT;")
    @ExpectedLocals(name = "p", type = "[[TP;")
    public <T extends List<Integer>, P, K extends Integer> void genericTypeWithoutParameters() {
        T list = null;
        list.add(1);
        int i = 0;
        P[][] p = null;
    }

    @ExpectedLocals(name = "this", type = "LLocalVariableTypeTableTest<TTHIS;>;")
    public void genericThis() {
    }

    @ExpectedLocals(name = "this", type = "LLocalVariableTypeTableTest<TTHIS;>;")
    @ExpectedLocals(name = "inWhile", type = "TTHIS;")
    @ExpectedLocals(name = "inTry", type = "TTHIS;")
    @ExpectedLocals(name = "inSync", type = "TTHIS;")
    @ExpectedLocals(name = "inDo", type = "TTHIS;")
    @ExpectedLocals(name = "inFor", type = "LLocalVariableTypeTableTest<-TTHIS;>;")
    @ExpectedLocals(name = "s", type = "Ljava/util/stream/Stream<+Ljava/lang/Integer;>;")
    public void deepScope() {
        {
            while (true) {
                THIS inWhile = null;
                for (LocalVariableTypeTableTest<? super THIS> inFor : Arrays.asList(this)) {
                    try (Stream<? extends Integer> s = Stream.of(0)) {
                        THIS inTry = null;
                        synchronized (this) {
                            THIS inSync = null;
                            do {
                                THIS inDo = null;
                                switch (1) {
                                    default:
                                        THIS inSwitch = null;
                                }
                            } while (true);
                        }
                    }
                }
            }
        }
    }

    class LocalVariableTypeTable implements VariableTable {

        final LocalVariableTypeTableAttribute att;


        public LocalVariableTypeTable(LocalVariableTypeTableAttribute att) {
            this.att = att;
        }

        @Override
        public int localVariableTableLength() {
            return att.localVariableTypes().size();
        }

        @Override
        public List<Entry> entries() {
            return att.localVariableTypes().stream().map(LocalVariableTypeTableEntry::new).collect(toList());
        }

        @Override
        public int attributeLength() {
            return ((BoundAttribute<?>)att).payloadLen();
        }

        private class LocalVariableTypeTableEntry implements Entry {

            final LocalVariableTypeInfo entry;

            private LocalVariableTypeTableEntry(LocalVariableTypeInfo entry) {
                this.entry = entry;
            }

            @Override
            public int index() {
                return entry.slot();
            }

            @Override
            public int startPC() {
                return entry.startPc();
            }

            @Override
            public int length() {
                return entry.length();
            }

            @Override
            public String name() {
                return entry.name().stringValue();
            }

            @Override
            public String type() {
                return entry.signature().stringValue();
            }

            @Override
            public String toString() {
                return dump();
            }
        }
    }
}
