/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import tests.Helper;


/*
 * @test
 * @summary Test basic linking from the run-time image with java.base.jmod missing
 *          but java.xml.jmod present. It should link from the run-time image without errors.
 * @requires (jlink.packagedModules & vm.compMode != "Xcomp" & os.maxMemory >= 2g)
 * @library ../../lib /test/lib
 * @enablePreview
 * @modules java.base/jdk.internal.jimage
 *          jdk.jlink/jdk.tools.jlink.internal
 *          jdk.jlink/jdk.tools.jlink.plugin
 *          jdk.jlink/jdk.tools.jimage
 * @build tests.* jdk.test.lib.process.OutputAnalyzer
 *        jdk.test.lib.process.ProcessTools
 * @run main/othervm -Xmx1g BasicJlinkMissingJavaBase
 */
public class BasicJlinkMissingJavaBase extends AbstractLinkableRuntimeTest {

    @Override
    public void runTest(Helper helper, boolean isLinkableRuntime) throws Exception {
        Path finalImage = createJavaXMLRuntimeLink(helper, "java-xml", isLinkableRuntime);
        verifyListModules(finalImage, List.of("java.base", "java.xml"));
    }

    private Path createJavaXMLRuntimeLink(Helper helper, String name, boolean isLinkableRuntime) throws Exception {
        BaseJlinkSpecBuilder builder = new BaseJlinkSpecBuilder();
        builder.helper(helper)
               .name(name)
               .addModule("java.xml")
               .validatingModule("java.xml");
        if (isLinkableRuntime) {
            builder.setLinkableRuntime();
        }
        Set<String> excludedJmods = Set.of("java.base.jmod");
        return createJavaImageRuntimeLink(builder.build(), excludedJmods);
    }

    public static void main(String[] args) throws Exception {
        BasicJlinkMissingJavaBase test = new BasicJlinkMissingJavaBase();
        test.run();
    }

}
