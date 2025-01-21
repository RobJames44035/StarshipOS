/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.jpackage.internal;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jdk.jpackage.internal.AppImageFile.LauncherInfo;
import static jdk.jpackage.internal.StandardBundlerParam.PREDEFINED_APP_IMAGE;

/**
 * Helper to install launchers as services for Unix installers.
 */
class UnixLaunchersAsServices extends ShellCustomAction {

    UnixLaunchersAsServices(PlatformPackage thePackage,
            List<String> requiredPackages, Map<String, Object> params,
            Function<LauncherInfo, UnixLauncherAsService> factory) throws
            IOException {

        this.thePackage = thePackage;
        this.requiredPackages = requiredPackages;

        // Read launchers information
        launchers = AppImageFile.getLaunchers(PREDEFINED_APP_IMAGE.fetchFrom(
                params), params).stream().filter(LauncherInfo::isService).map(
                factory::apply).toList();
    }

    @Override
    final List<String> requiredPackages() {
        if (launchers.isEmpty()) {
            return Collections.emptyList();
        } else {
            return requiredPackages;
        }
    }

    @Override
    protected List<String> replacementStringIds() {
        return REPLACEMENT_STRING_IDS;
    }

    @Override
    protected Map<String, String> createImpl() throws IOException {
        Map<String, String> data = new HashMap<>();

        if (launchers.isEmpty()) {
            return data;
        }

        var installedDescriptorFiles = launchers.stream().map(
                launcher -> enqouter.applyTo(launcher.descriptorFilePath(
                        Path.of("/")).toString())).toList();

        Function<String, String> strigifier = cmd -> {
            return stringifyShellCommands(Stream.of(List.of(
                    cmd), installedDescriptorFiles).flatMap(x -> x.stream()).collect(
                    Collectors.joining(" ")));
        };

        data.put(SCRIPTS, stringifyTextFile("services_utils.sh"));

        data.put(COMMANDS_INSTALL, strigifier.apply("register_services"));
        data.put(COMMANDS_UNINSTALL, strigifier.apply("unregister_services"));

        for (var launcher : launchers) {
            launcher.getResource().saveToFile(launcher.descriptorFilePath(
                    thePackage.sourceRoot()));
        }

        return data;
    }

    boolean isEmpty() {
        return launchers.isEmpty();
    }

    abstract static class UnixLauncherAsService extends LauncherAsService {

        UnixLauncherAsService(String name, Map<String, Object> mainParams,
                OverridableResource resource) {
            super(name, mainParams, resource);
        }

        abstract Path descriptorFilePath(Path root);
    }

    private final PlatformPackage thePackage;
    private final List<String> requiredPackages;
    private final List<UnixLauncherAsService> launchers;
    private final Enquoter enqouter = Enquoter.forShellLiterals();

    private static final String COMMANDS_INSTALL = "LAUNCHER_AS_SERVICE_COMMANDS_INSTALL";
    private static final String COMMANDS_UNINSTALL = "LAUNCHER_AS_SERVICE_COMMANDS_UNINSTALL";
    private static final String SCRIPTS = "LAUNCHER_AS_SERVICE_SCRIPTS";

    protected static final List<String> REPLACEMENT_STRING_IDS = List.of(
            COMMANDS_INSTALL, COMMANDS_UNINSTALL, SCRIPTS);
}
