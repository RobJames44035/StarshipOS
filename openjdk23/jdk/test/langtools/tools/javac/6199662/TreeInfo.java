/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package p;
import p.Tree.*;

public class TreeInfo {

    public static void declarationFor(final Tree tree) {
        class DeclScanner extends TreeScanner<Error> {
            public void scan(Tree tree) {
            }
        }
        DeclScanner s = new DeclScanner();
        tree.accept(s);
    }
}
