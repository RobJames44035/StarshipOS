/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import jdk.internal.foreign.abi.Binding;
import jdk.internal.foreign.abi.CallingSequence;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class CallArrangerTestBase {

    public static void checkArgumentBindings(CallingSequence callingSequence, Binding[][] argumentBindings) {
        assertEquals(callingSequence.argumentBindingsCount(), argumentBindings.length,
          callingSequence.asString() + " != " + Arrays.deepToString(argumentBindings));

        for (int i = 0; i < callingSequence.argumentBindingsCount(); i++) {
            List<Binding> actual = callingSequence.argumentBindings(i);
            Binding[] expected = argumentBindings[i];
            assertEquals(actual, Arrays.asList(expected), "bindings at: " + i + ": " + actual + " != " + Arrays.toString(expected));
        }
    }

    public static void checkReturnBindings(CallingSequence callingSequence, Binding[] returnBindings) {
        assertEquals(callingSequence.returnBindings(), Arrays.asList(returnBindings), callingSequence.returnBindings() + " != " + Arrays.toString(returnBindings));
    }
}
