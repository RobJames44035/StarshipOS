/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

/**
 * @test
 * @bug 8328316
 * @summary Testing Gatherer behavior under short circuiting
 * @run junit GathererShortCircuitTest
 */

public class GathererShortCircuitTest {
    @Test
    public void mustBeAbleToPushFromFinisher() {
        Integer expected = 8328316;
        List<Integer> source = List.of(1,2,3,4,5);

        Gatherer<Integer, ?, Integer> pushOneInFinisher =
                Gatherer.of(
                    (_, element, downstream) -> false,
                    (_, downstream) -> downstream.push(expected)
                );

        var usingCollect =
            source.stream().gather(pushOneInFinisher).collect(Collectors.toList());
        var usingBuiltin =
            source.stream().gather(pushOneInFinisher).toList();
        var usingCollectPar =
            source.stream().parallel().gather(pushOneInFinisher).collect(Collectors.toList());
        var usingBuiltinPar =
            source.stream().parallel().gather(pushOneInFinisher).toList();

        assertEquals(List.of(expected), usingCollect);
        assertEquals(List.of(expected), usingBuiltin);
        assertEquals(List.of(expected), usingCollectPar);
        assertEquals(List.of(expected), usingBuiltinPar);
    }
}
