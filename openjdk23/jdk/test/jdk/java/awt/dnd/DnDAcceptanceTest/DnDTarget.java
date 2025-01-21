/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import java.awt.Button;
import java.awt.Color;
import java.awt.Panel;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

class DnDTarget extends Panel implements DropTargetListener {
    Color bgColor;
    Color htColor;

    DnDTarget(Color bgColor, Color htColor) {
        super();
        this.bgColor = bgColor;
        this.htColor = htColor;
        setBackground(bgColor);
        setDropTarget(new DropTarget(this, this));
    }

    public void dragEnter(DropTargetDragEvent e) {
        System.err.println("[Target] dragEnter");
        e.acceptDrag(DnDConstants.ACTION_COPY);
        setBackground(htColor);
        repaint();
    }

    public void dragOver(DropTargetDragEvent e) {
        System.err.println("[Target] dragOver");
        e.acceptDrag(DnDConstants.ACTION_COPY);
    }

    public void dragExit(DropTargetEvent e) {
        System.err.println("[Target] dragExit");
        setBackground(bgColor);
        repaint();
    }

    public void drop(DropTargetDropEvent dtde) {
        System.err.println("[Target] drop");
        DropTargetContext dtc = dtde.getDropTargetContext();

        if ((dtde.getSourceActions() & DnDConstants.ACTION_COPY) != 0) {
            dtde.acceptDrop(DnDConstants.ACTION_COPY);
        } else {
            dtde.rejectDrop();
            return;
        }

        DataFlavor[] dfs = dtde.getCurrentDataFlavors();
        if (dfs != null && dfs.length >= 1) {
            Transferable transfer = dtde.getTransferable();
            Object obj;
            try {
                obj = transfer.getTransferData(dfs[0]);
            } catch (IOException | UnsupportedFlavorException ex) {
                System.err.println(ex.getMessage());
                dtc.dropComplete(false);
                return;
            }

            if (obj != null) {
                Button button;
                try {
                    button = (Button) obj;
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    dtc.dropComplete(false);
                    return;
                }
                add(button);
                repaint();
            }
        }
        setBackground(bgColor);
        invalidate();
        validate();
        repaint();
        dtc.dropComplete(true);
    }

    public void dropActionChanged(DropTargetDragEvent e) {
    System.err.println("[Target] dropActionChanged");
    }
}
