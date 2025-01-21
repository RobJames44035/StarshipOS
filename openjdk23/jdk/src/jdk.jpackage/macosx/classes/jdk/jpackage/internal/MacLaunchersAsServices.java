/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.jpackage.internal;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import static jdk.jpackage.internal.MacAppImageBuilder.MAC_CF_BUNDLE_IDENTIFIER;
import static jdk.jpackage.internal.OverridableResource.createResource;
import static jdk.jpackage.internal.StandardBundlerParam.APP_NAME;
import jdk.jpackage.internal.util.PathUtils;

/**
 * Helper to install launchers as services using "launchd".
 */
public final class MacLaunchersAsServices extends UnixLaunchersAsServices {

    private MacLaunchersAsServices(PlatformPackage thePackage,
            Map<String, Object> params) throws IOException {
        super(thePackage, List.of(), params, li -> {
            return new Launcher(thePackage, li.getName(), params);
        });
    }

    static ShellCustomAction create(Map<String, Object> params,
            Path outputDir) throws IOException {
        if (StandardBundlerParam.isRuntimeInstaller(params)) {
            return null;
        }
        return Optional.of(new MacLaunchersAsServices(new PlatformPackage() {
            @Override
            public String name() {
                return MAC_CF_BUNDLE_IDENTIFIER.fetchFrom(params);
            }

            @Override
            public Path sourceRoot() {
                return outputDir;
            }

            @Override
            public ApplicationLayout sourceApplicationLayout() {
                throw new UnsupportedOperationException();
            }

            @Override
            public ApplicationLayout installedApplicationLayout() {
                return ApplicationLayout.macAppImage().resolveAt(Path.of(
                        MacBaseInstallerBundler.getInstallDir(params, false),
                        APP_NAME.fetchFrom(params) + ".app"));
            }
        }, params)).filter(Predicate.not(MacLaunchersAsServices::isEmpty)).orElse(
                null);
    }

    public static Path getServicePListFileName(String packageName,
            String launcherName) {
        String baseName = launcherName.replaceAll("[\\s]", "_");
        return Path.of(packageName + "-" + baseName + ".plist");
    }

    private static class Launcher extends UnixLauncherAsService {

        Launcher(PlatformPackage thePackage, String name,
                Map<String, Object> mainParams) {
            super(name, mainParams, createResource("launchd.plist.template",
                    mainParams).setCategory(I18N.getString(
                            "resource.launchd-plist-file")));

            plistFilename = getServicePListFileName(thePackage.name(), getName());

            // It is recommended to set value of "label" property in launchd
            // .plist file equal to the name of this .plist file without the suffix.
            String label = PathUtils.replaceSuffix(plistFilename.getFileName(), "").toString();

            getResource()
                    .setPublicName(plistFilename)
                    .addSubstitutionDataEntry("LABEL", label)
                    .addSubstitutionDataEntry("APPLICATION_LAUNCHER",
                            thePackage.installedApplicationLayout().launchersDirectory().resolve(
                                    getName()).toString());
        }

        @Override
        Path descriptorFilePath(Path root) {
            return root.resolve("Library/LaunchDaemons").resolve(plistFilename);
        }

        private final Path plistFilename;
    }
}
