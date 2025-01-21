/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.executor;

import java.util.List;
import nsk.share.Pair;
import vm.runtime.defmeth.shared.MemoryClassLoader;
import vm.runtime.defmeth.shared.data.Tester;

public interface TestExecutor {

    public MemoryClassLoader getLoader();

    public List<Pair<Tester,Throwable>> run();

    public void run(Tester test) throws Throwable;
}
