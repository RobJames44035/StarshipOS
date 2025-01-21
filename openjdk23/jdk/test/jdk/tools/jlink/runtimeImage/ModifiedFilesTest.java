/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import tests.Helper;

public abstract class ModifiedFilesTest extends AbstractLinkableRuntimeTest {

    abstract String initialImageName();
    abstract void testAndAssert(Path modifiedFile, Helper helper, Path initialImage) throws Exception;

    @Override
    void runTest(Helper helper, boolean isLinkableRuntime) throws Exception {
        BaseJlinkSpecBuilder builder = new BaseJlinkSpecBuilder()
                .name(initialImageName())
                .addModule("java.base")
                .validatingModule("java.base")
                .helper(helper);
        if (isLinkableRuntime) {
            builder.setLinkableRuntime();
        }
        Path initialImage = createRuntimeLinkImage(builder.build());

        Path netPropertiesFile = modifyFileInImage(initialImage);

        testAndAssert(netPropertiesFile, helper, initialImage);
    }

    protected Path modifyFileInImage(Path jmodLessImg)
            throws IOException, AssertionError {
        // modify net.properties config file
        Path netPropertiesFile = jmodLessImg.resolve("conf").resolve("net.properties");
        Properties props = new Properties();
        try (InputStream is = Files.newInputStream(netPropertiesFile)) {
            props.load(is);
        }
        String prevVal = (String)props.put("java.net.useSystemProxies", Boolean.TRUE.toString());
        if (prevVal == null || Boolean.getBoolean(prevVal) != false) {
            throw new AssertionError("Expected previous value to be false!");
        }
        try (OutputStream out = Files.newOutputStream(netPropertiesFile)) {
            props.store(out, "Modified net.properties file!");
        }
        return netPropertiesFile;
    }
}
