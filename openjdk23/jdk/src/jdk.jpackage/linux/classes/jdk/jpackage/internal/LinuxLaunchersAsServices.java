/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.jpackage.internal;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static jdk.jpackage.internal.OverridableResource.createResource;

/**
 * Helper to install launchers as services using "systemd".
 */
public final class LinuxLaunchersAsServices extends UnixLaunchersAsServices {

    private LinuxLaunchersAsServices(PlatformPackage thePackage,
            Map<String, Object> params) throws IOException {
        super(thePackage, REQUIRED_PACKAGES, params, li -> {
            return new Launcher(thePackage, li.getName(), params);
        });
    }

    @Override
    protected List<String> replacementStringIds() {
        return LINUX_REPLACEMENT_STRING_IDS;
    }

    @Override
    protected Map<String, String> createImpl() throws IOException {
        var data = super.createImpl();
        if (!data.isEmpty()) {
            data.put(COMMON_SCRIPTS, stringifyTextFile("common_utils.sh"));
        }
        return data;
    }

    static ShellCustomAction create(PlatformPackage thePackage,
            Map<String, Object> params) throws IOException {
        if (StandardBundlerParam.isRuntimeInstaller(params)) {
            return ShellCustomAction.nop(LINUX_REPLACEMENT_STRING_IDS);
        }
        return new LinuxLaunchersAsServices(thePackage, params);
    }

    public static Path getServiceUnitFileName(String packageName,
            String launcherName) {
        String baseName = launcherName.replaceAll("[\\s]", "_");
        return Path.of(packageName + "-" + baseName + ".service");
    }

    private static class Launcher extends UnixLauncherAsService {

        Launcher(PlatformPackage thePackage, String name,
                Map<String, Object> mainParams) {
            super(name, mainParams, createResource("unit-template.service",
                    mainParams).setCategory(I18N.getString(
                            "resource.systemd-unit-file")));

            unitFilename = getServiceUnitFileName(thePackage.name(), getName());

            getResource()
                    .setPublicName(unitFilename)
                    .addSubstitutionDataEntry("APPLICATION_LAUNCHER",
                            Enquoter.forPropertyValues().applyTo(
                                    thePackage.installedApplicationLayout().launchersDirectory().resolve(
                                            getName()).toString()));
        }

        @Override
        Path descriptorFilePath(Path root) {
            return root.resolve("lib/systemd/system").resolve(unitFilename);
        }

        private final Path unitFilename;
    }

    private static final List<String> REQUIRED_PACKAGES = List.of("systemd",
            "coreutils" /* /usr/bin/wc */, "grep");

    private static final String COMMON_SCRIPTS = "COMMON_SCRIPTS";

    private static final List<String> LINUX_REPLACEMENT_STRING_IDS;

    static {
        ArrayList<String> buf = new ArrayList<>();
        buf.addAll(UnixLaunchersAsServices.REPLACEMENT_STRING_IDS);
        buf.add(COMMON_SCRIPTS);

        LINUX_REPLACEMENT_STRING_IDS = Collections.unmodifiableList(buf);
    }
}
