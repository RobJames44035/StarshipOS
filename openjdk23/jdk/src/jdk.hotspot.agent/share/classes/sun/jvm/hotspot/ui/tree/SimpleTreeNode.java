/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.ui.tree;

import javax.swing.*;
import javax.swing.tree.*;

/** A very simple interface for nodes in the SimpleTreeModel. */

public interface SimpleTreeNode {
  public int getChildCount();
  public SimpleTreeNode getChild(int index);
  public boolean isLeaf();
  public int getIndexOfChild(SimpleTreeNode child);

  /** TreeTable support: get name of the node */
  public String getName();

  /** TreeTable support: get value of the node */
  public String getValue();
}
