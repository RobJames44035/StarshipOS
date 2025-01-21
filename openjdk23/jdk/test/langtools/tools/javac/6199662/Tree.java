/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6199662 6325201 6726015
 * @summary javac: compilation success depends on compilation order
 *
 * @compile Tree.java TreeScanner.java TreeInfo.java
 * @compile TreeInfo.java TreeScanner.java Tree.java
 *
 * @compile -XDcompilePolicy=bytodo Tree.java TreeScanner.java TreeInfo.java
 * @compile -XDcompilePolicy=bytodo TreeInfo.java TreeScanner.java Tree.java
 *
 * @compile -XDcompilePolicy=byfile Tree.java TreeScanner.java TreeInfo.java
 * @compile -XDcompilePolicy=byfile TreeInfo.java TreeScanner.java Tree.java
 *
 * @compile -XDcompilePolicy=simple Tree.java TreeScanner.java TreeInfo.java
 * @compile -XDcompilePolicy=simple TreeInfo.java TreeScanner.java Tree.java
 *
 * @compile -XDshould-stop.ifError=FLOW -XDshould-stop.ifNoError=FLOW  Tree.java TreeScanner.java TreeInfo.java
 * @compile -XDshould-stop.ifError=FLOW -XDshould-stop.ifNoError=FLOW  TreeInfo.java TreeScanner.java Tree.java
 *
 * @compile -XDshould-stop.ifError=ATTR -XDshould-stop.ifNoError=ATTR  Tree.java TreeScanner.java TreeInfo.java
 * @compile -XDshould-stop.ifError=ATTR -XDshould-stop.ifNoError=ATTR  TreeInfo.java TreeScanner.java Tree.java
 */

package p;

public abstract class Tree {

    /** Visit this tree with a given visitor.
     */
    public abstract <E extends Throwable> void accept(Visitor<E> v) throws E;


    /** A generic visitor class for trees.
     */
    public static abstract class Visitor<E extends Throwable> {
        public void visitTree(Tree that)                   throws E { assert false; }
    }
}
