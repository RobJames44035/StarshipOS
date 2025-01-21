/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package jdk.jpackage.internal;

public class LinuxAppBundler extends AppImageBundler {
    public LinuxAppBundler() {
        setAppImageSupplier(LinuxAppImageBuilder::new);
    }
}
