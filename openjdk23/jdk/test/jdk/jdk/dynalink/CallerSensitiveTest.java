/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import jdk.dynalink.beans.BeansLinker;
import org.testng.annotations.Test;

/**
 * @test
 * @build ClassLoaderAware
 * @run testng CallerSensitiveTest
 */
public class CallerSensitiveTest {
    @Test
    public void testCallerSensitive() {
        new BeansLinker().getLinkerForClass(ClassLoaderAware.class);
    }
}
