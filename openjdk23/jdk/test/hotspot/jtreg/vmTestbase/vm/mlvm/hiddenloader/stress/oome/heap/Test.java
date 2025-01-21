/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */


/*
 * @test
 * @key randomness
 * @modules java.base/jdk.internal.misc
 *
 * @summary converted from VM Testbase vm/mlvm/anonloader/stress/oome/heap.
 * VM Testbase keywords: [feature_mlvm, nonconcurrent]
 *
 * @library /vmTestbase
 *          /test/lib
 *
 * @comment build test class and indify classes
 * @build vm.mlvm.hiddenloader.stress.oome.heap.Test
 * @run driver vm.mlvm.share.IndifiedClassesBuilder
 *
 * @run main/othervm -XX:-UseGCOverheadLimit -Xmx128m vm.mlvm.hiddenloader.stress.oome.heap.Test
 */

package vm.mlvm.hiddenloader.stress.oome.heap;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.List;
import java.io.IOException;

import vm.mlvm.hiddenloader.share.HiddenkTestee01;
import vm.mlvm.share.MlvmOOMTest;
import vm.mlvm.share.MlvmTestExecutor;
import vm.mlvm.share.Env;
import vm.mlvm.share.FileUtils;

/**
 * This test loads a class using defineHiddenClass, creates instances
 * of that class and stores them, expecting Heap OOME.
 *
 */

public class Test extends MlvmOOMTest {
    @Override
    protected void checkOOME(OutOfMemoryError oome) {
        String message = oome.getMessage();
        if (!message.startsWith("Java heap space")) {
            throw new RuntimeException("TEST FAIL : wrong OOME", oome);
        }
    }
    @Override
    protected void eatMemory(List<Object> list) {
        byte[] classBytes = null;
        try {
            classBytes = FileUtils.readClass(HiddenkTestee01.class.getName());
        } catch (IOException e) {
            Env.throwAsUncheckedException(e);
        }
        try {
            while (true) {
                Lookup lookup = MethodHandles.lookup();
                Lookup ank_lookup = MethodHandles.privateLookupIn(HiddenkTestee01.class, lookup);
                Class<?> c = ank_lookup.defineHiddenClass(classBytes, true).lookupClass();
                list.add(c.newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            Env.throwAsUncheckedException(e);
        }
    }

    public static void main(String[] args) {
        MlvmTestExecutor.launch(args);
    }
}
