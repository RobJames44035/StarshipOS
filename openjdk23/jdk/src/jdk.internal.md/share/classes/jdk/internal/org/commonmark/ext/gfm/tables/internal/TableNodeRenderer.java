/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, a notice that is now available elsewhere in this distribution
 * accompanied the original version of this file, and, per its terms,
 * should not be removed.
 */

package jdk.internal.org.commonmark.ext.gfm.tables.internal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jdk.internal.org.commonmark.ext.gfm.tables.TableBlock;
import jdk.internal.org.commonmark.ext.gfm.tables.TableBody;
import jdk.internal.org.commonmark.ext.gfm.tables.TableCell;
import jdk.internal.org.commonmark.ext.gfm.tables.TableHead;
import jdk.internal.org.commonmark.ext.gfm.tables.TableRow;
import jdk.internal.org.commonmark.node.Node;
import jdk.internal.org.commonmark.renderer.NodeRenderer;

abstract class TableNodeRenderer implements NodeRenderer {

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<>(Arrays.asList(
                TableBlock.class,
                TableHead.class,
                TableBody.class,
                TableRow.class,
                TableCell.class
        ));
    }

    @Override
    public void render(Node node) {
        if (node instanceof TableBlock) {
            renderBlock((TableBlock) node);
        } else if (node instanceof TableHead) {
            renderHead((TableHead) node);
        } else if (node instanceof TableBody) {
            renderBody((TableBody) node);
        } else if (node instanceof TableRow) {
            renderRow((TableRow) node);
        } else if (node instanceof TableCell) {
            renderCell((TableCell) node);
        }
    }

    protected abstract void renderBlock(TableBlock node);

    protected abstract void renderHead(TableHead node);

    protected abstract void renderBody(TableBody node);

    protected abstract void renderRow(TableRow node);

    protected abstract void renderCell(TableCell node);
}
