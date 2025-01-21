/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Test exclude files plugin
 * @author Jean-Francois Denise
 * @modules jdk.jlink/jdk.tools.jlink.internal
 *          jdk.jlink/jdk.tools.jlink.internal.plugins
 *          jdk.jlink/jdk.tools.jlink.plugin
 * @run main ExcludeFilesPluginTest
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import jdk.tools.jlink.internal.ResourcePoolManager;

import jdk.tools.jlink.internal.plugins.ExcludeFilesPlugin;
import jdk.tools.jlink.plugin.ResourcePool;
import jdk.tools.jlink.plugin.ResourcePoolEntry;

public class ExcludeFilesPluginTest {
    public static void main(String[] args) throws Exception {
        new ExcludeFilesPluginTest().test();
    }

    public void test() throws Exception {
        checkFiles("**.jcov", "num/toto.jcov", "", true);
        checkFiles("**.jcov", "/toto.jcov", "", true);
        checkFiles("**.jcov", "toto.jcov/tutu/tata", "", false);
        checkFiles("/java.base/*.jcov", "toto.jcov", "java.base", true);
        checkFiles("/java.base/toto.jcov", "iti.jcov", "t/java.base", false);
        checkFiles("/java.base/*/toto.jcov", "toto.jcov", "java.base", false);
        checkFiles("/java.base/*/toto.jcov", "tutu/toto.jcov", "java.base", true);
        checkFiles("**/java.base/*/toto.jcov", "java.base/tutu/toto.jcov", "/tutu", true);

        checkFiles("/**$*.properties", "tutu/Toto$Titi.properties", "java.base", true);
        checkFiles("**$*.properties", "tutu/Toto$Titi.properties", "java.base", true);

        // Excluded files list in a file
        File order = new File("files.exc");
        order.createNewFile();
        Files.write(order.toPath(), "**.jcov".getBytes());
        checkFiles("@" + order.getAbsolutePath(), "/num/toto.jcov", "", true);
    }

    public void checkFiles(String s, String sample, String module, boolean exclude) throws Exception {
        Map<String, String> prop = new HashMap<>();
        ExcludeFilesPlugin fplug = new ExcludeFilesPlugin();
        prop.put(fplug.getName(), s);
        fplug.configure(prop);
        ResourcePoolManager files = new ResourcePoolManager();
        ResourcePoolManager fresult = new ResourcePoolManager();
        ResourcePoolEntry f = ResourcePoolEntry.create("/" + module + "/" + sample,
                ResourcePoolEntry.Type.CONFIG, new byte[0]);
        files.add(f);

        ResourcePool resPool = fplug.transform(files.resourcePool(), fresult.resourcePoolBuilder());

        if (exclude) {
            if (resPool.contains(f)) {
                throw new Exception(sample + " should be excluded by " + s);
            }
        } else {
            if (!resPool.contains(f)) {
                throw new Exception(sample + " shouldn't be excluded by " + s);
            }
        }
    }
}
