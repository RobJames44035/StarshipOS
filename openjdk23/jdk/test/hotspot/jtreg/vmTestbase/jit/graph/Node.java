/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package jit.graph;

// This class define the tree node.

public class Node {
    public final static int Black = 0;    // constants used to define the
    public final static int Red = 1;      // node color
    public final static int Left_son = 2; // constants used to identify
    public final static int Right_son = 3;// the node parent and sons.
    public final static int Parent = 4;

    private int color;
    private int key;
    private Node L, R, P;      // L-left son,R-right son,P-parent

    // constructor create a new node the default color is red
    // the default appearance (bold) is regular.
    // initialize the key field.

    public Node(int k) {
        color = Red;
        key = k;
        L = null;
        R = null;
        P = null;
    }

    // constructor for constructing a tree null object, is color
    // is black.

    public Node() {
        color = Black;
        key = -1;
        L = null;
        R = null;
        P = null;
    }

    // This method set the node key.

    public void setKey(int k) {
        key = k;
    }

    // This method return the node key.

    public int getKey() {
        return (key);
    }

    // This method set the node color.

    public void setColor(int c) {
        if (c == Black) {
            color = Black;
        } else if (c == Red) {
            color = Red;
        }
    }

    // This method return the node color.

    public int getColor() {
        return (color);
    }

    // This method set the node parent or childs acording to the who
    // parameter.

    public void setNode(int who, Node n) {
        switch (who) {
            case Left_son:
                L = n;
                break;
            case Right_son:
                R = n;
                break;
            case Parent:
                P = n;
                break;
        }
    }

    // This method return the node parent or childs acording to the who
    // parameter.

    public Node getNode(int who) {
        switch (who) {
            case Left_son:
                return L;
            case Right_son:
                return R;
            case Parent:
                return P;
        }
        return null;
    }
}
