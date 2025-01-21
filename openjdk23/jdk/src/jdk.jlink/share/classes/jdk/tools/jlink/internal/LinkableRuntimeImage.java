/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package jdk.tools.jlink.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import jdk.tools.jlink.internal.runtimelink.ResourceDiff;

/**
 * Class that supports the feature of running jlink based on the current
 * run-time image.
 */
public class LinkableRuntimeImage {

    // meta-data files per module for supporting linking from the run-time image
    public static final String RESPATH_PATTERN = "jdk/tools/jlink/internal/runtimelink/fs_%s_files";
    // The diff files per module for supporting linking from the run-time image
    public static final String DIFF_PATTERN = "jdk/tools/jlink/internal/runtimelink/diff_%s";

    /**
     * In order to be able to show whether or not a runtime is capable of
     * linking from it in {@code jlink --help} we need to look for the delta
     * files in the {@code jdk.jlink} module. If present we have the capability.
     *
     * @return {@code true} iff this jlink is capable of linking from the
     *         run-time image.
     */
    public static boolean isLinkableRuntime() {
        try (InputStream in = getDiffInputStream("java.base")) {
            return in != null;
        } catch (IOException e) {
            // fall-through
        }
        return false;
    }

    private static InputStream getDiffInputStream(String module) throws IOException {
        String resourceName = String.format(DIFF_PATTERN, module);
        return LinkableRuntimeImage.class.getModule().getResourceAsStream(resourceName);
    }

    public static Archive newArchive(String module,
                                     Path path,
                                     boolean ignoreModifiedRuntime,
                                     TaskHelper taskHelper) {
        assert isLinkableRuntime();
        // Here we retrieve the per module difference file, which is
        // potentially empty, from the modules image and pass that on to
        // JRTArchive for further processing. When streaming resources from
        // the archive, the diff is being applied.
        List<ResourceDiff> perModuleDiff = null;
        try (InputStream in = getDiffInputStream(module)){
            perModuleDiff = ResourceDiff.read(in);
        } catch (IOException e) {
            throw new AssertionError("Failure to retrieve resource diff for " +
                                     "module " + module, e);
        }
        return new JRTArchive(module, path, !ignoreModifiedRuntime, perModuleDiff, taskHelper);
    }


}
