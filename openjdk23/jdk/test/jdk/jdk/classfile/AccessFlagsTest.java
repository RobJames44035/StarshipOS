/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @summary Testing ClassFile AccessFlags.
 * @run junit AccessFlagsTest
 */
import java.lang.classfile.ClassFile;
import java.lang.constant.ClassDesc;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.lang.reflect.AccessFlag;
import java.lang.classfile.AccessFlags;

import static java.lang.classfile.ClassFile.ACC_STATIC;
import static java.lang.constant.ConstantDescs.CD_int;
import static java.lang.constant.ConstantDescs.MTD_void;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.ParameterizedTest;

class AccessFlagsTest {

    @ParameterizedTest
    @EnumSource(names = { "CLASS", "METHOD", "FIELD" })
    void testRandomAccessFlagsConverions(AccessFlag.Location ctx) {
        IntFunction<AccessFlags> intFactory = switch (ctx) {
            case CLASS -> v -> {
                var bytes = ClassFile.of().build(ClassDesc.of("Test"), clb -> clb.withFlags(v));
                return ClassFile.of().parse(bytes).flags();
            };
            case METHOD -> v -> {
                var bytes = ClassFile.of().build(ClassDesc.of("Test"), clb ->
                        clb.withMethod("test", MTD_void, v & ACC_STATIC, mb -> mb.withFlags(v)));
                return ClassFile.of().parse(bytes).methods().getFirst().flags();
            };
            case FIELD -> v -> {
                var bytes = ClassFile.of().build(ClassDesc.of("Test"), clb ->
                        clb.withField("test", CD_int, fb -> fb.withFlags(v)));
                return ClassFile.of().parse(bytes).fields().getFirst().flags();
            };
            default -> null;
        };
        Function<AccessFlag[], AccessFlags> flagsFactory = switch (ctx) {
            case CLASS -> v -> {
                var bytes = ClassFile.of().build(ClassDesc.of("Test"), clb -> clb.withFlags(v));
                return ClassFile.of().parse(bytes).flags();
            };
            case METHOD -> v -> {
                boolean hasStatic = Arrays.stream(v).anyMatch(f -> f == AccessFlag.STATIC);
                var bytes = ClassFile.of().build(ClassDesc.of("Test"), clb ->
                        clb.withMethod("test", MTD_void, hasStatic ? ACC_STATIC : 0, mb -> mb.withFlags(v)));
                return ClassFile.of().parse(bytes).methods().getFirst().flags();
            };
            case FIELD -> v -> {
                var bytes = ClassFile.of().build(ClassDesc.of("Test"), clb ->
                        clb.withField("test", CD_int, fb -> fb.withFlags(v)));
                return ClassFile.of().parse(bytes).fields().getFirst().flags();
            };
            default -> null;
        };

        var allFlags = EnumSet.allOf(AccessFlag.class);
        allFlags.removeIf(f -> !f.locations().contains(ctx));

        var r = new Random(123);
        for (int i = 0; i < 1000; i++) {
            var randomFlags = allFlags.stream().filter(f -> r.nextBoolean()).toArray(AccessFlag[]::new);
            assertEquals(intFactory.apply(flagsFactory.apply(randomFlags).flagsMask()).flags(), Set.of(randomFlags));

            var randomMask = r.nextInt(Short.MAX_VALUE);
            assertEquals(intFactory.apply(randomMask).flagsMask(), randomMask);
        }
    }

    @Test
    void testInvalidFlagsUse() {
        ClassFile.of().build(ClassDesc.of("Test"), clb -> {
            assertThrowsForInvalidFlagsUse(clb::withFlags);
            clb.withMethod("test", MTD_void, ACC_STATIC, mb -> assertThrowsForInvalidFlagsUse(mb::withFlags));
            clb.withField("test", CD_int, fb -> assertThrowsForInvalidFlagsUse(fb::withFlags));
        });
    }

    void assertThrowsForInvalidFlagsUse(Consumer<AccessFlag[]> factory) {
        assertThrows(IllegalArgumentException.class, () -> factory.accept(AccessFlag.values()));
    }
}
