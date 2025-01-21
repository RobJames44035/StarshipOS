/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package org.openjdk.tests.java.util.stream;

import java.util.List;
import java.util.stream.LambdaTestHelpers;
import java.util.stream.OpTestCase;
import java.util.stream.Stream;
import java.util.stream.StreamTestDataProvider;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.stream.TestData;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.LambdaTestHelpers.*;

/**
 * ReduceByOpTest
 *
 * @author Brian Goetz
 */
@Test
public class ReduceByOpTest extends OpTestCase {

    @Test(dataProvider = "StreamTestData<Integer>", dataProviderClass = StreamTestDataProvider.class)
    public void testOps(String name, TestData.OfRef<Integer> data) {
        Map<Boolean,List<Integer>> gbResult = data.stream().collect(groupingBy(LambdaTestHelpers.forPredicate(pEven, true, false)));
        Map<Boolean, Integer> result = data.stream().collect(groupingBy(LambdaTestHelpers.forPredicate(pEven, true, false), reducing(0, rPlus)));
        assertEquals(result.size(), gbResult.size());
        for (Map.Entry<Boolean, Integer> entry : result.entrySet()) {
            setContext("entry", entry);
            Boolean key = entry.getKey();
            assertEquals(entry.getValue(), data.stream().filter(e -> pEven.test(e) == key).reduce(0, rPlus));
        }

        int uniqueSize = data.into(new HashSet<Integer>()).size();
        Map<Integer, List<Integer>> mgResult = exerciseTerminalOps(data, s -> s.collect(groupingBy(mId)));
        Map<Integer, Integer> miResult = exerciseTerminalOps(data, s -> s.collect(groupingBy(mId, reducing(0, e -> 1, Integer::sum))));
        assertEquals(miResult.keySet().size(), uniqueSize);
        for (Map.Entry<Integer, Integer> entry : miResult.entrySet()) {
            setContext("entry", entry);
            assertEquals((int) entry.getValue(), mgResult.get(entry.getKey()).size());
        }
    }
}
