/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.tools.jlink.internal.plugins;

/**
 * Plugin to add VM command-line options, by storing them in a resource
 * that's read by the VM at startup
 */
public final class AddOptionsPlugin extends AddResourcePlugin {

    public AddOptionsPlugin() {
        super("add-options", "/java.base/jdk/internal/vm/options");
    }

}
