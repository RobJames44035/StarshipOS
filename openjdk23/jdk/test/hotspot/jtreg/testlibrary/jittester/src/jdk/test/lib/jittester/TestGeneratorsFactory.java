/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.test.lib.jittester;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TestGeneratorsFactory implements Function<String[], List<TestsGenerator>> {

    @Override
    public List<TestsGenerator> apply(String[] input) {
        List<TestsGenerator> result = new ArrayList<>();
        for (String generatorName : input) {
            switch (generatorName) {
                case "JavaCode":
                    result.add(new JavaCodeGenerator());
                    break;
                case "ByteCode":
                    result.add(new ByteCodeGenerator());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown generator: " + generatorName);
            }
        }
        return result;
    }
}
