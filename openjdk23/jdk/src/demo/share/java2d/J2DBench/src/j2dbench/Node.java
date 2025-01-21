/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * This source code is provided to illustrate the usage of a given feature
 * or technique and has been deliberately simplified. Additional steps
 * required for a production-quality application, such as security checks,
 * input validation and proper error handling, might not be present in
 * this sample code.
 */


package j2dbench;

import java.io.PrintWriter;
import javax.swing.JLabel;
import javax.swing.JComponent;

public abstract class Node {
    private String nodeName;
    private String description;
    private Group parent;
    private Node next;

    protected Node() {
    }

    public Node(Group parent, String nodeName, String description) {
        this.parent = parent;
        this.nodeName = nodeName;
        this.description = description;
        parent.addChild(this);
    }

    public Group getParent() {
        return parent;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getTreeName() {
        String name = nodeName;
        if (parent != null) {
            String pname = parent.getTreeName();
            if (pname != null) {
                name = pname + "." + name;
            }
        }
        return name;
    }

    public String getDescription() {
        return description;
    }

    public JComponent getJComponent() {
        return (nodeName != null) ? new JLabel(description) : null;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node node) {
        this.next = node;
    }

    public void traverse(Visitor v) {
        v.visit(this);
    }

    public abstract void restoreDefault();

    public abstract void write(PrintWriter pw);

    public abstract String setOption(String key, String value);

    public static interface Visitor {
        public void visit(Node node);
    }

    public static interface Iterator {
        public boolean hasNext();
        public Node next();
    }
}
