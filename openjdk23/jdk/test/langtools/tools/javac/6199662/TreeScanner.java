/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package p;
import p.Tree.*;

public class TreeScanner<E extends Throwable> extends Visitor<E> {

    /** Visitor method: Scan a single node.
     */
    public void scan(Tree tree) throws E {
        if(tree!=null) tree.accept(this);
    }
}
