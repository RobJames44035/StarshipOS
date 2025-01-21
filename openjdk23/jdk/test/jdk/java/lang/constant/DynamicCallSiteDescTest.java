/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.lang.invoke.MethodType;
import java.lang.constant.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.testng.annotations.Test;

import static java.lang.constant.ConstantDescs.CD_int;
import static java.lang.constant.ConstantDescs.CD_void;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * @test
 * @compile DynamicCallSiteDescTest.java
 * @run testng DynamicCallSiteDescTest
 * @summary unit tests for java.lang.constant.DynamicCallSiteDesc
 */

@Test
public class DynamicCallSiteDescTest extends SymbolicDescTest {
    /* note there is no unit test for method resolveCallSiteDesc as it is being tested in another test in this
     * suite, IndyDescTest
     */

    public void testOf() throws ReflectiveOperationException {
        DirectMethodHandleDesc dmh = ConstantDescs.ofCallsiteBootstrap(
                ClassDesc.of("BootstrapAndTarget"),
                "bootstrap",
                ClassDesc.of("java.lang.invoke.CallSite")
        );
        try {
            DynamicCallSiteDesc.of(
                    dmh,
                    "",
                    MethodTypeDesc.ofDescriptor("()I")
            );
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException iae) {
            // good
        }

        try {
            DynamicCallSiteDesc.of(
                    null,
                    "getTarget",
                    MethodTypeDesc.ofDescriptor("()I")
            );
            fail("NullPointerException expected");
        } catch (NullPointerException npe) {
            // good
        }

        try {
            DynamicCallSiteDesc.of(
                    dmh,
                    null,
                    MethodTypeDesc.ofDescriptor("()I")
            );
            fail("NullPointerException expected");
        } catch (NullPointerException npe) {
            // good
        }

        try {
            DynamicCallSiteDesc.of(
                    dmh,
                    "getTarget",
                    null
            );
            fail("NullPointerException expected");
        } catch (NullPointerException npe) {
            // good
        }

        try {
            DynamicCallSiteDesc.of(
                    dmh,
                    "getTarget",
                    MethodTypeDesc.ofDescriptor("()I"),
                    null
            );
            fail("NullPointerException expected");
        } catch (NullPointerException npe) {
            // good
        }
        try {
            DynamicCallSiteDesc.of(
                    dmh,
                    "getTarget",
                    MethodTypeDesc.ofDescriptor("()I"),
                    new ConstantDesc[]{ null }
            );
            fail("NullPointerException expected");
        } catch (NullPointerException npe) {
            // good
        }
    }

    public void testWithArgs() throws ReflectiveOperationException {
        DynamicCallSiteDesc desc = DynamicCallSiteDesc.of(ConstantDescs.ofCallsiteBootstrap(
                ClassDesc.of("BootstrapAndTarget"),
                "bootstrap",
                ClassDesc.of("java.lang.invoke.CallSite")
            ),
            "getTarget",
            MethodTypeDesc.ofDescriptor("()I")
        );

        try {
            desc.withArgs(null);
            fail("NullPointerException expected");
        } catch (NullPointerException npe) {
            // good
        }

        try {
            desc.withArgs(new ConstantDesc[]{ null });
            fail("NullPointerException expected");
        } catch (NullPointerException npe) {
            // good
        }
    }

    public void testWithNameAndType() throws ReflectiveOperationException {
        DynamicCallSiteDesc desc = DynamicCallSiteDesc.of(ConstantDescs.ofCallsiteBootstrap(
                ClassDesc.of("BootstrapAndTarget"),
                "bootstrap",
                ClassDesc.of("java.lang.invoke.CallSite")
                ),
                "getTarget",
                MethodTypeDesc.ofDescriptor("()I")
        );

        try {
            desc.withNameAndType(null, MethodTypeDesc.ofDescriptor("()I"));
            fail("NullPointerException expected");
        } catch (NullPointerException npe) {
            // good
        }

        try {
            desc.withNameAndType("bootstrap", null);
            fail("NullPointerException expected");
        } catch (NullPointerException npe) {
            // good
        }
    }

    public void testAccessorsAndFactories() throws ReflectiveOperationException {
        DynamicCallSiteDesc desc = DynamicCallSiteDesc.of(ConstantDescs.ofCallsiteBootstrap(
                ClassDesc.of("BootstrapAndTarget"),
                "bootstrap",
                ClassDesc.of("java.lang.invoke.CallSite")
                ),
                "_",
                MethodTypeDesc.ofDescriptor("()I")
        );
        assertEquals(desc, DynamicCallSiteDesc.of((DirectMethodHandleDesc)desc.bootstrapMethod(), desc.invocationType()));
        assertEquals(desc, DynamicCallSiteDesc.of((DirectMethodHandleDesc)desc.bootstrapMethod(),
                desc.invocationName(), desc.invocationType()));
        assertEquals(desc, DynamicCallSiteDesc.of((DirectMethodHandleDesc)desc.bootstrapMethod(),
                desc.invocationName(), desc.invocationType(), desc.bootstrapArgs()));
    }
}
