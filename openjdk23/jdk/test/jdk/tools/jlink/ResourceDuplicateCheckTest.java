/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8168254
 * @summary Detect duplicated resources in packaged modules
 * @modules jdk.jlink/jdk.tools.jlink.builder
 *          jdk.jlink/jdk.tools.jlink.internal
 *          jdk.jlink/jdk.tools.jlink.plugin
 * @run build ResourceDuplicateCheckTest
 * @run main ResourceDuplicateCheckTest
 */

import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import jdk.tools.jlink.builder.DefaultImageBuilder;
import jdk.tools.jlink.internal.Platform;
import jdk.tools.jlink.internal.ResourcePoolEntryFactory;
import jdk.tools.jlink.internal.ResourcePoolManager;
import jdk.tools.jlink.plugin.PluginException;
import jdk.tools.jlink.plugin.ResourcePoolEntry;

public class ResourceDuplicateCheckTest {
    public static void main(String[] args) throws Exception {
        new ResourceDuplicateCheckTest().test();
    }

    public void test() throws Exception {
        ResourcePoolManager input = new ResourcePoolManager();
        // need java.base module info because OS name is retrieved from it from storeFiles
        input.add(ResourcePoolEntryFactory.create("/java.base/module-info.class",
                    ResourcePoolEntry.Type.CLASS_OR_RESOURCE, getJavaBaseModuleInfo()));

        // same NATIVE_CMD from two different modules
        input.add(newInMemoryImageFile("/com.acme/bin/myexec",
                    ResourcePoolEntry.Type.NATIVE_CMD, "mylib"));
        input.add(newInMemoryImageFile("/com.foo/bin/myexec",
                    ResourcePoolEntry.Type.NATIVE_CMD, "mylib"));
        Path root = Paths.get(System.getProperty("test.classes"));
        DefaultImageBuilder writer = new DefaultImageBuilder(root, Collections.emptyMap(),
                Platform.runtime());
        try {
            writer.storeFiles(input.resourcePool());
        } catch (PluginException pe) {
            if (! pe.getMessage().contains("Duplicate resources:")) {
                throw new AssertionError("expected duplicate resources message");
            }
        }
    }

    private byte[] getJavaBaseModuleInfo() throws Exception {
        Path path = FileSystems.
                getFileSystem(URI.create("jrt:/")).
                getPath("/modules/java.base/module-info.class");
        return Files.readAllBytes(path);
    }

    private static ResourcePoolEntry newInMemoryImageFile(String path,
            ResourcePoolEntry.Type type, String content) {
        return ResourcePoolEntryFactory.create(path, type, content.getBytes());
    }
}
