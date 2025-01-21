/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package jdk.jpackage.internal;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import jdk.jpackage.internal.util.FileUtils;


/**
 * AbstractBundler
 *
 * This is the base class all bundlers extend from.
 * It contains methods and parameters common to all bundlers.
 * The concrete implementations are in the platform specific bundlers.
 */
abstract class AbstractBundler implements Bundler {

    static final BundlerParamInfo<Path> IMAGES_ROOT =
            new StandardBundlerParam<>(
            "imagesRoot",
            Path.class,
            params ->
                StandardBundlerParam.TEMP_ROOT.fetchFrom(params).resolve("images"),
            (s, p) -> null);

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public void cleanup(Map<String, ? super Object> params) {
        try {
            FileUtils.deleteRecursive(
                    StandardBundlerParam.TEMP_ROOT.fetchFrom(params));
        } catch (IOException e) {
            Log.verbose(e.getMessage());
        }
    }
}
