/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package jdk.jpackage.internal;

public class WinAppBundler extends AppImageBundler {
    public WinAppBundler() {
        setAppImageSupplier(WindowsAppImageBuilder::new);
    }
}
