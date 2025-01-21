/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package gc.stringdedup;

/*
 * @test id=Serial
 * @summary Test string deduplication during young GC
 * @bug 8029075
 * @requires vm.gc.Serial
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc:open
 * @modules java.base/java.lang:open
 *          java.management
 * @run driver gc.stringdedup.TestStringDeduplicationYoungGC Serial
 */

/*
 * @test id=G1
 * @summary Test string deduplication during young GC
 * @bug 8029075
 * @requires vm.gc.G1
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc:open
 * @modules java.base/java.lang:open
 *          java.management
 * @run driver gc.stringdedup.TestStringDeduplicationYoungGC G1
 */

/*
 * @test id=Parallel
 * @summary Test string deduplication during young GC
 * @bug 8029075
 * @requires vm.gc.Parallel
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc:open
 * @modules java.base/java.lang:open
 *          java.management
 * @run driver gc.stringdedup.TestStringDeduplicationYoungGC Parallel
 */

/*
 * @test id=Shenandoah
 * @summary Test string deduplication during young GC
 * @bug 8029075
 * @requires vm.gc.Shenandoah
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc:open
 * @modules java.base/java.lang:open
 *          java.management
 * @run driver gc.stringdedup.TestStringDeduplicationYoungGC Shenandoah
 */

/*
 * @test id=Z
 * @summary Test string deduplication during young GC
 * @bug 8029075
 * @requires vm.gc.Z
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc:open
 * @modules java.base/java.lang:open
 *          java.management
 * @run driver gc.stringdedup.TestStringDeduplicationYoungGC Z
 */

public class TestStringDeduplicationYoungGC {
    public static void main(String[] args) throws Exception {
        TestStringDeduplicationTools.selectGC(args);
        TestStringDeduplicationTools.testYoungGC();
    }
}
