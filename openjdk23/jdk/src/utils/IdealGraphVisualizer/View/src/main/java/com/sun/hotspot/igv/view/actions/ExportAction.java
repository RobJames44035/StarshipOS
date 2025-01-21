/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.view.actions;

import com.sun.hotspot.igv.settings.Settings;
import com.sun.hotspot.igv.view.ExportCookie;
import java.io.File;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.*;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.CallableSystemAction;


/**
 * @author Thomas Wuerthinger
 */
@ActionID(category = "File", id = "com.sun.hotspot.igv.view.actions.ExportAction")
@ActionRegistration(displayName = "#CTL_ExportAction")
@ActionReferences({
        @ActionReference(path = "Menu/File", position = 710),
        @ActionReference(path = "Shortcuts", name = "D-E")
})
@Messages({
        "CTL_ExportAction=Export current graph...",
        "HINT_ExportAction=Export current graph as image file"
})
public final class ExportAction extends CallableSystemAction implements LookupListener {

    private final Lookup.Result<ExportCookie> result;

    public ExportAction() {
        putValue(Action.SHORT_DESCRIPTION, NbBundle.getMessage(ExportAction.class, "HINT_ExportAction"));
        putValue(Action.SMALL_ICON , ImageUtilities.loadImageIcon(iconResource(), true));
        Lookup lookup = Utilities.actionsGlobalContext();
        result = lookup.lookup(new Lookup.Template<>(ExportCookie.class));
        result.addLookupListener(this);
        resultChanged(null);
    }

    @Override
    public void resultChanged(LookupEvent e) {
        super.setEnabled(result.allInstances().size() > 0);
    }

    @Override
    public void performAction() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                String lcFileName = f.getName().toLowerCase();
                return lcFileName.endsWith(".pdf") ||
                       lcFileName.endsWith(".svg") ||
                       f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Image files (*.pdf, *.svg)";
            }
        });
        fc.setCurrentDirectory(new File(Settings.get().get(Settings.DIRECTORY, Settings.DIRECTORY_DEFAULT)));

        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (!file.getName().contains(".")) {
                file = new File(file.getAbsolutePath() + ".pdf");
            }

            File dir = file;
            if (!dir.isDirectory()) {
                dir = dir.getParentFile();
            }

            Settings.get().put(Settings.DIRECTORY, dir.getAbsolutePath());
            ExportCookie cookie = Utilities.actionsGlobalContext().lookup(ExportCookie.class);
            if (cookie != null) {
                cookie.export(file);
            }
        }
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(ExportAction.class, "CTL_ExportAction");
    }

    @Override
    protected String iconResource() {
        return "com/sun/hotspot/igv/view/images/export.png"; // NOI18N
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}
