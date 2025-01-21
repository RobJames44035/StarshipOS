/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8331291 8332614
 * @summary Testing Attributes API and ClassReader.
 * @run junit AttributesTest
 */
import java.lang.classfile.AttributeMapper;
import java.lang.classfile.AttributedElement;
import java.lang.classfile.Attributes;
import java.lang.classfile.BufWriter;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassReader;
import java.lang.classfile.CustomAttribute;
import java.lang.classfile.constantpool.ConstantPoolException;
import java.lang.classfile.constantpool.InvokeDynamicEntry;
import java.lang.classfile.constantpool.Utf8Entry;
import java.lang.constant.ClassDesc;
import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import jdk.internal.classfile.impl.BoundAttribute;
import jdk.internal.classfile.impl.TemporaryConstantPool;

class AttributesTest {

    @Test
    void testAttributesMapping() throws Exception {
        var cp = TemporaryConstantPool.INSTANCE;
        for (Field f : Attributes.class.getDeclaredFields()) {
            if (f.getName().startsWith("NAME_") && f.getType() == String.class) {
                Utf8Entry attrName = cp.utf8Entry((String)f.get(null));
                AttributeMapper<?> mapper = BoundAttribute.standardAttribute(attrName);
                assertNotNull(mapper, attrName.stringValue() + " 0x" + Integer.toHexString(attrName.hashCode()));
                assertEquals(attrName.stringValue(), mapper.name());
            }
        }
    }

    private static final String TEST_ATTRIBUTE_NAME = "org.openjdk.classfile.test";
    private static final AttributeMapper<TestAttribute> TEST_MAPPER = new AttributeMapper<>() {
        @Override
        public String name() {
            return TEST_ATTRIBUTE_NAME;
        }

        @Override
        public TestAttribute readAttribute(AttributedElement enclosing, ClassReader cf, int pos) {
            int cpPos = pos - 6; // Attribute Name Utf8
            // Test valid pos/index - NPE
            assertThrows(NullPointerException.class, () -> cf.readEntry(cpPos, null));
            assertThrows(NullPointerException.class, () -> cf.readEntryOrNull(cpPos, null));
            assertThrows(NullPointerException.class, () -> cf.entryByIndex(1, null));

            // Test valid pos/index - incorrect type
            assertThrows(ConstantPoolException.class, () -> cf.readEntry(cpPos, InvokeDynamicEntry.class));
            assertThrows(ConstantPoolException.class, () -> cf.readEntryOrNull(cpPos, InvokeDynamicEntry.class));
            assertThrows(ConstantPoolException.class, () -> cf.entryByIndex(1, InvokeDynamicEntry.class));

            // Passing tests
            var utf8 = cf.readEntry(cpPos, Utf8Entry.class);
            assertSame(utf8, cf.readEntryOrNull(cpPos, Utf8Entry.class));

            // Test invalid pos/index - NPE thrown before CPE
            assertThrows(NullPointerException.class, () -> cf.readEntry(-1, null));
            assertThrows(NullPointerException.class, () -> cf.readEntryOrNull(-1, null));
            assertThrows(NullPointerException.class, () -> cf.entryByIndex(-1, null));

            return new TestAttribute(true);
        }

        @Override
        public void writeAttribute(BufWriter buf, TestAttribute attr) {
            buf.writeIndex(buf.constantPool().utf8Entry(name()));
            buf.writeInt(0);
        }

        @Override
        public AttributeStability stability() {
            return AttributeStability.STATELESS;
        }
    };

    private static final class TestAttribute extends CustomAttribute<TestAttribute> {
        final boolean fromMapper;

        TestAttribute(boolean fromMapper) {
            super(TEST_MAPPER);
            this.fromMapper = fromMapper;
        }
    }

    @Test
    void testClassReader() throws Exception {
        var cf = ClassFile.of(ClassFile.AttributeMapperOption.of(utf8 -> {
            if (utf8.equalsString(TEST_ATTRIBUTE_NAME)) {
                return TEST_MAPPER;
            }
            return null;
        }));

        var cd = ClassDesc.of("Testing");
        var bytes = cf.build(cd, clb -> clb
                .with(new TestAttribute(false)));
        assertTrue(cf.parse(bytes)
                .findAttribute(TEST_MAPPER)
                .orElseThrow()
                .fromMapper);
    }
}
