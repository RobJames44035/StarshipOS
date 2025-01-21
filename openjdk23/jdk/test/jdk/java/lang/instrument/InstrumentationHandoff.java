/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.lang.instrument.Instrumentation;

/**
 * A JPLIS agent that makes the Instrumentation available via a static accessor.
 * Used so that unit test frameworks that run as apps can exercise the Instrumentation--
 * configure this guy as a JPLIS agent, then call the Instrumentation fetcher from the test case.
 *
 */
public class InstrumentationHandoff
{
    private static Instrumentation      sInstrumentation;

    // disallow construction
    private
    InstrumentationHandoff()
        {
        }

    public static void
    premain(String options, Instrumentation inst)
    {
        System.out.println("InstrumentationHandoff JPLIS agent initialized");
        sInstrumentation = inst;
    }

    // may return null
    public static Instrumentation
    getInstrumentation()
    {
        return sInstrumentation;
    }

    public static Instrumentation
    getInstrumentationOrThrow()
    {
        Instrumentation result = getInstrumentation();
        if ( result == null )
            {
            throw new NullPointerException("instrumentation instance not initialized");
            }
        return result;
    }


}
