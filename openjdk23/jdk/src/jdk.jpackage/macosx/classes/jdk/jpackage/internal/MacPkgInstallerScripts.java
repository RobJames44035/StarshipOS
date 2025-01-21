/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.jpackage.internal;

import java.util.function.Supplier;
import jdk.jpackage.internal.PackageScripts.ResourceConfig;

/**
 * MacOS PKG installer scripts.
 */
final class MacPkgInstallerScripts {

    enum AppScripts implements Supplier<OverridableResource> {
        preinstall(new ResourceConfig("preinstall.template",
                "resource.pkg-preinstall-script")),
        postinstall(new ResourceConfig("postinstall.template",
                "resource.pkg-postinstall-script"));

        AppScripts(ResourceConfig cfg) {
            this.cfg = cfg;
        }

        @Override
        public OverridableResource get() {
            return cfg.createResource();
        }

        private final ResourceConfig cfg;
    }

    enum ServicesScripts implements Supplier<OverridableResource> {
        preinstall(new ResourceConfig("services-preinstall.template",
                "resource.pkg-services-preinstall-script")),
        postinstall(new ResourceConfig("services-postinstall.template",
                "resource.pkg-services-postinstall-script"));

        ServicesScripts(ResourceConfig cfg) {
            this.cfg = cfg;
        }

        @Override
        public OverridableResource get() {
            return cfg.createResource();
        }

        private final ResourceConfig cfg;
    }

    static PackageScripts<AppScripts> createAppScripts() {
        return PackageScripts.create(AppScripts.class);
    }

    static PackageScripts<ServicesScripts> createServicesScripts() {
        return PackageScripts.create(ServicesScripts.class);
    }
}
