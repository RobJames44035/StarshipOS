/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.executor;

import nsk.share.Pair;
import vm.runtime.defmeth.shared.Constants;
import vm.runtime.defmeth.shared.DefMethTest;
import vm.runtime.defmeth.shared.MemoryClassLoader;
import vm.runtime.defmeth.shared.Util;
import vm.runtime.defmeth.shared.builder.TestBuilder;
import vm.runtime.defmeth.shared.data.Clazz;
import vm.runtime.defmeth.shared.data.ParamValueExtractor;
import vm.runtime.defmeth.shared.data.Tester;
import vm.runtime.defmeth.shared.data.method.param.Param;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Commmon ancestor for reflection-based tests (Method.invoke & MethodHandle.invokeWithArguments).
 * Encapsulates all necessary state for test execution and contains some utility methods.
 */
public abstract class AbstractReflectionTest implements TestExecutor {
    protected MemoryClassLoader cl;
    protected Collection<? extends Tester> tests;
    protected DefMethTest testInstance;

    public AbstractReflectionTest(DefMethTest testInstance, MemoryClassLoader cl, Collection<? extends Tester> tests) {
        this.testInstance = testInstance;
        this.cl = cl;
        this.tests = tests;
    }

    @Override
    public MemoryClassLoader getLoader() {
        return cl;
    }

    protected Class[] paramType(String desc) throws ClassNotFoundException {
        Pair<String[],String> p = Util.parseDesc(desc);
        Class[] ptypes = new Class[p.first.length];
        for (int i = 0; i < ptypes.length; i++) {
            ptypes[i] = Util.decodeClass(p.first[i], getLoader());
        }
        return ptypes;
    }

    public Class resolve(Clazz clazz) {
        try {
            return cl.loadClass(clazz.name());
        } catch (ClassNotFoundException e) {
            throw new Error(e);
        }
    }

    protected Object[] values(Param[] params) {
        Object[] result = new Object[params.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new ParamValueExtractor(this, params[i]).value();
        }
        return result;
    }

    public List<Pair<Tester,Throwable>> run() {
        List<Pair<Tester,Throwable>> errors = new ArrayList<>();

        if (tests.isEmpty()) {
            throw new IllegalStateException("No tests to run");
        }

        for (Tester t : tests) {
            StringBuilder msg =
                    new StringBuilder(String.format("\t%-30s: ", t.name()));

            Throwable error = null;
            try {
                run(t);

                msg.append("PASSED");
            } catch (Throwable e) {
                error = e;
                errors.add(Pair.of(t,e));
                msg.append("FAILED");
            } finally {
                testInstance.getLog().info(msg.toString());
                if (error != null) {
                    //testInstance.getLog().info("\t\t"+error.getMessage());
                    testInstance.getLog().info("\t\t"+error);
                    if (Constants.PRINT_STACK_TRACE) {
                        error.printStackTrace();
                    }
                }
            }
        }

        testInstance.addFailureCount(errors.isEmpty() ? 0 : 1);
        return errors;
    }
}
