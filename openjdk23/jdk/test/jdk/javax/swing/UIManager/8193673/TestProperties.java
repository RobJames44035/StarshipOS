/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * @test
 * @bug 8193673
 * @summary The test verifies that l&f specific properties are accessible
 */
public final class TestProperties {

    private static final String[] windowsProperties = {
            "FileChooser.viewMenuButtonToolTipText",
            "FileChooser.viewMenuButtonAccessibleName",
    };

    private static final String[] aquaProperties = {
            "FileChooser.mac.newFolder",
    };

    private static final String[] gtkProperties = {
            "FileChooser.renameFileDialogText",
    };

    private static final String[] motifProperties = {
            "FileChooser.enterFolderNameLabel.textAndMnemonic",
    };

    private static final String[] nimbusProperties = {
            "FileChooser.refreshActionLabelText",
    };

    private static final String[] metalProperties = {
            "MetalTitlePane.iconify.titleAndMnemonic",
    };

    public static void main(final String[] args) throws Exception {
        UIManager.setLookAndFeel(new MetalLookAndFeel());
        test(metalProperties);

        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        test(nimbusProperties);

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        test(motifProperties);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            test(windowsProperties);
        } catch (Exception e) {
            // ignore
        }

        try {
            UIManager.setLookAndFeel("com.apple.laf.AquaLookAndFeel");
            test(aquaProperties);
        } catch (Exception e) {
            // ignore
        }

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            test(gtkProperties);
        } catch (Exception e) {
            // ignore
        }
    }

    private static void test(final String[] properties) {
        for (final String name : properties) {
            String value = UIManager.getString(name);
            if (value == null) {
                System.err.println("Current LookAndFeel = "
                        + UIManager.getLookAndFeel().getDescription());
                System.err.printf("The value for %s property is null\n", name);
                throw new Error();
            }
        }
    }
}
