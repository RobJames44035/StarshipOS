/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.ui.tree;

import java.util.*;

/** Node to which others can be added; suitable for use as root node
    of graph */

public class SimpleTreeGroupNode implements SimpleTreeNode {
  private List<SimpleTreeNode> children;

  public SimpleTreeGroupNode() {
    children = new ArrayList<>();
  }

  public int getChildCount() { return children.size(); }
  public SimpleTreeNode getChild(int index) {
    return children.get(index);
  }
  public void addChild(SimpleTreeNode child) {
    children.add(child);
  }
  public SimpleTreeNode removeChild(int index) {
    return children.remove(index);
  }
  public void removeAllChildren() {
    children.clear();
  }
  public boolean isLeaf() {
    return false;
  }
  public int getIndexOfChild(SimpleTreeNode child) {
    return children.indexOf(child);
  }

  public String getName()  { return null; }
  public String getValue() { return null; }
}
