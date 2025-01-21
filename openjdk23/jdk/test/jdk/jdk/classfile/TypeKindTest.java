/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @summary Testing TypeKind.
 * @bug 8331744
 * @run junit TypeKindTest
 */
import org.junit.jupiter.api.Test;

import java.lang.classfile.TypeKind;

import static org.junit.Assert.assertThrows;

class TypeKindTest {
    @Test
    void testContracts() {
        assertThrows(NullPointerException.class, () -> TypeKind.from(null));

        assertThrows(NullPointerException.class, () -> TypeKind.fromDescriptor(null));
        assertThrows(IllegalArgumentException.class, () -> TypeKind.fromDescriptor(""));
        assertThrows(IllegalArgumentException.class, () -> TypeKind.fromDescriptor("int"));

        assertThrows(IllegalArgumentException.class, () -> TypeKind.fromNewarrayCode(-1));
        assertThrows(IllegalArgumentException.class, () -> TypeKind.fromNewarrayCode(21));
    }
}
