/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.jpackage.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import static jdk.jpackage.internal.StandardBundlerParam.APP_NAME;
import static jdk.jpackage.internal.StandardBundlerParam.ICON;
import static jdk.jpackage.internal.StandardBundlerParam.ADD_LAUNCHERS;
import jdk.jpackage.internal.util.PathUtils;

public class LinuxAppImageBuilder extends AbstractAppImageBuilder {

    static final BundlerParamInfo<Path> ICON_PNG =
            new StandardBundlerParam<>(
            "icon.png",
            Path.class,
            params -> {
                Path f = ICON.fetchFrom(params);
                if (f != null && f.getFileName() != null && !f.getFileName()
                        .toString().toLowerCase().endsWith(".png")) {
                    Log.error(MessageFormat.format(
                            I18N.getString("message.icon-not-png"), f));
                    return null;
                }
                return f;
            },
            (s, p) -> Path.of(s));

    static final String DEFAULT_ICON = "JavaApp.png";

    LinuxAppImageBuilder(Path imageOutDir) {
        super(imageOutDir);
    }

    private void writeEntry(InputStream in, Path dstFile) throws IOException {
        Files.createDirectories(dstFile.getParent());
        Files.copy(in, dstFile);
    }

    public static String getLauncherName(Map<String, ? super Object> params) {
        return APP_NAME.fetchFrom(params);
    }

    @Override
    public void prepareApplicationFiles(Map<String, ? super Object> params)
            throws IOException {
        appLayout.roots().stream().forEach(dir -> {
            try {
                IOUtils.writableOutputDir(dir);
            } catch (PackagerException pe) {
                throw new RuntimeException(pe);
            }
        });

        // create the primary launcher
        createLauncherForEntryPoint(params, null);

        // create app launcher shared library
        createLauncherLib();

        // create the additional launchers, if any
        List<Map<String, ? super Object>> entryPoints
                = ADD_LAUNCHERS.fetchFrom(params);
        for (Map<String, ? super Object> entryPoint : entryPoints) {
            createLauncherForEntryPoint(AddLauncherArguments.merge(params,
                    entryPoint, ICON.getID(), ICON_PNG.getID()), params);
        }

        // Copy class path entries to Java folder
        copyApplication(params);
    }

    private void createLauncherLib() throws IOException {
        Path path = appLayout.pathGroup().getPath(
                ApplicationLayout.PathRole.LINUX_APPLAUNCHER_LIB);
        try (InputStream resource = getResourceAsStream("libjpackageapplauncheraux.so")) {
            writeEntry(resource, path);
        }

        path.toFile().setExecutable(true, false);
        path.toFile().setWritable(true, true);
    }

    private void createLauncherForEntryPoint(Map<String, ? super Object> params,
            Map<String, ? super Object> mainParams) throws IOException {
        // Copy executable to launchers folder
        Path executableFile = appLayout.launchersDirectory().resolve(getLauncherName(params));
        try (InputStream is_launcher =
                getResourceAsStream("jpackageapplauncher")) {
            writeEntry(is_launcher, executableFile);
        }

        executableFile.toFile().setExecutable(true, false);
        executableFile.toFile().setWritable(true, true);

        writeCfgFile(params);

        var iconResource = createIconResource(DEFAULT_ICON, ICON_PNG, params,
                mainParams);
        if (iconResource != null) {
            Path iconTarget = appLayout.destktopIntegrationDirectory().resolve(
                    APP_NAME.fetchFrom(params) + PathUtils.getSuffix(Path.of(
                    DEFAULT_ICON)));
            iconResource.saveToFile(iconTarget);
        }
    }
}
