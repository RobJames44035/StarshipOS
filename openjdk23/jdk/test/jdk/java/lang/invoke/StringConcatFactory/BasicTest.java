/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8187089
 * @run main BasicTest
 */

import java.lang.invoke.*;
import java.util.Arrays;

public class BasicTest {
    static final int MAX_PARAM_SLOTS = 200;
    static int exceedMaxParamSlots = 0;
    public static void main(String[] args) throws Throwable {
        int expectionTestCases = 0;

        Class<?>[] types = new Class<?>[200];
        Arrays.fill(types, int.class);
        test(MethodType.methodType(String.class, types));

        types = new Class<?>[100];
        Arrays.fill(types, long.class);
        test(MethodType.methodType(String.class, types));

        // test cases exceeding 200 parameter slots
        expectionTestCases++;
        types = new Class<?>[101];
        Arrays.fill(types, 0, 50, long.class);
        Arrays.fill(types, 50, 100, double.class);
        types[100] = int.class;
        test(MethodType.methodType(String.class, types));

        expectionTestCases++;
        types = new Class<?>[201];
        Arrays.fill(types, int.class);
        test(MethodType.methodType(String.class, types));

        if (exceedMaxParamSlots != expectionTestCases) {
            throw new RuntimeException("expected one test case exceeding 200 param slots");
        }
    }

    /**
     * Tests if StringConcatException is thrown if the given concatType
     * has more than 200 parameter slots
     */
    static void test(MethodType concatType) throws StringConcatException {
        String recipe = "";
        int slots = 0;
        for (Class<?> c : concatType.parameterList()) {
            recipe += "\1";
            slots++;
            if (c == double.class || c == long.class) {
                slots++;
            }
        }
        if (slots > MAX_PARAM_SLOTS) {
            exceedMaxParamSlots++;
        }
        System.out.format("Test %s parameter slots%n", slots);
        try {
            StringConcatFactory.makeConcat(MethodHandles.lookup(), "name", concatType);
            if (slots > MAX_PARAM_SLOTS) {
                throw new RuntimeException("StringConcatException not thrown");
            }
        } catch (StringConcatException e) {
            if (slots <= MAX_PARAM_SLOTS) throw e;
        }

        try {
            StringConcatFactory.makeConcatWithConstants(MethodHandles.lookup(), "name",
                                                        concatType, recipe);
            if (slots > MAX_PARAM_SLOTS) {
                throw new RuntimeException("StringConcatException not thrown");
            }
        } catch (StringConcatException e) {
            if (slots <= MAX_PARAM_SLOTS) throw e;
        }
    }
}
