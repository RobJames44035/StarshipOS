/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.tools.jlink.internal.plugins;

/**
 * Plugin to set the vendor version string, by redefining the static field
 * java.lang.VersionProps.VENDOR_VERSION
 */
public final class VendorVersionPlugin extends VersionPropsPlugin {

    public VendorVersionPlugin() {
        super("VENDOR_VERSION");
    }

}
