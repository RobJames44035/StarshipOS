/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import java.util.Map;

import jdk.tools.jlink.internal.ResourcePoolManager;
import jdk.tools.jlink.internal.plugins.DefaultStripDebugPlugin;
import jdk.tools.jlink.internal.plugins.DefaultStripDebugPlugin.NativePluginFactory;
import jdk.tools.jlink.plugin.Plugin;
import jdk.tools.jlink.plugin.ResourcePool;
import jdk.tools.jlink.plugin.ResourcePoolBuilder;
import jdk.tools.jlink.plugin.ResourcePoolEntry;
import jdk.tools.jlink.plugin.ResourcePoolEntry.Type;

/*
 * @test
 * @summary Test for combination of java debug attributes stripping and
 * native debug symbols stripping.
 * @modules jdk.jlink/jdk.tools.jlink.internal
 *          jdk.jlink/jdk.tools.jlink.internal.plugins
 *          jdk.jlink/jdk.tools.jlink.plugin
 * @run main/othervm DefaultStripDebugPluginTest
 */
public class DefaultStripDebugPluginTest {

    public void testWithNativeStripPresent() {
        MockStripPlugin javaPlugin = new MockStripPlugin(false);
        MockStripPlugin nativePlugin = new MockStripPlugin(true);
        TestNativeStripPluginFactory nativeFactory =
                                 new TestNativeStripPluginFactory(nativePlugin);
        DefaultStripDebugPlugin plugin = new DefaultStripDebugPlugin(javaPlugin,
                                                                     nativeFactory);
        ResourcePoolManager inManager = new ResourcePoolManager();
        ResourcePool pool = plugin.transform(inManager.resourcePool(),
                                             inManager.resourcePoolBuilder());
        if (!pool.findEntry(MockStripPlugin.JAVA_PATH).isPresent() ||
            !pool.findEntry(MockStripPlugin.NATIVE_PATH).isPresent()) {
            throw new AssertionError("Expected both native and java to get called");
        }
    }

    public void testNoNativeStripPluginPresent() {
        MockStripPlugin javaPlugin = new MockStripPlugin(false);
        TestNativeStripPluginFactory nativeFactory =
                                         new TestNativeStripPluginFactory(null);
        DefaultStripDebugPlugin plugin = new DefaultStripDebugPlugin(javaPlugin,
                                                                     nativeFactory);
        ResourcePoolManager inManager = new ResourcePoolManager();
        ResourcePool pool = plugin.transform(inManager.resourcePool(),
                                             inManager.resourcePoolBuilder());
        if (!pool.findEntry(MockStripPlugin.JAVA_PATH).isPresent()) {
            throw new AssertionError("Expected java strip plugin to get called");
        }
    }

    public static void main(String[] args) {
        DefaultStripDebugPluginTest test = new DefaultStripDebugPluginTest();
        test.testNoNativeStripPluginPresent();
        test.testWithNativeStripPresent();
    }

    public static class MockStripPlugin implements Plugin {

        private static final String NATIVE_PATH = "/foo/lib/test.so.debug";
        private static final String JAVA_PATH = "/foo/TestClass.class";
        private static final String STRIP_NATIVE_NAME = "strip-native-debug-symbols";
        private static final String OMIT_ARG = "exclude-debuginfo-files";
        private final boolean isNative;

        MockStripPlugin(boolean isNative) {
            this.isNative = isNative;
        }

        @Override
        public void configure(Map<String, String> config) {
            if (isNative) {
                if (config.get(STRIP_NATIVE_NAME) == null ||
                    !config.get(STRIP_NATIVE_NAME).equals(OMIT_ARG)) {
                    throw new AssertionError("Test failed!, Expected native " +
                                             "plugin to be properly configured.");
                } else {
                    System.out.println("DEBUG: native plugin properly configured with: " +
                                       STRIP_NATIVE_NAME + "=" + config.get(STRIP_NATIVE_NAME));
                }
            }
        }

        @Override
        public ResourcePool transform(ResourcePool in,
                                      ResourcePoolBuilder out) {
            in.transformAndCopy((r) -> {return r; }, out); // identity
            String resPath = JAVA_PATH;
            ResourcePoolEntry.Type type = Type.CLASS_OR_RESOURCE;
            if (isNative) {
                resPath = NATIVE_PATH;
                type = Type.NATIVE_LIB;
            }
            ResourcePoolEntry entry = createMockEntry(resPath, type);
            out.add(entry);
            return out.build();
        }

        private ResourcePoolEntry createMockEntry(String path,
                                                  ResourcePoolEntry.Type type) {
            byte[] mockContent = new byte[] { 0, 1, 2, 3 };
            ResourcePoolEntry entry = ResourcePoolEntry.create(path,
                                                               type,
                                                               mockContent);
            return entry;
        }

    }

    public static class TestNativeStripPluginFactory implements NativePluginFactory {

        private final MockStripPlugin plugin;

        TestNativeStripPluginFactory(MockStripPlugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public Plugin create() {
            return plugin;
        }

    }
}
