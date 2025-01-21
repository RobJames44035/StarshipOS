/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package pkg;

/**
 * Qualified Link: {@link pkg.C.InnerC}.<br/>
 * Unqualified Link1: {@link C.InnerC}.<br/>
 * Unqualified Link2: {@link InnerC}.<br/>
 * Qualified Link: {@link #method(pkg.C.InnerC, pkg.C.InnerC2)}.<br/>
 * Unqualified Link: {@link #method(C.InnerC, C.InnerC2)}.<br/>
 * Unqualified Link: {@link #method(InnerC, InnerC2)}.<br/>
 * Link w/o Signature: {@link #method}.<br/>
 * Package Link: {@link pkg}.<br/>
 *
 *
 */
public class C {

    public InnerC MEMBER = new InnerC();
    /**
     *  A red herring inner class to confuse the matching, thus to
     *  ensure the right one is linked.
     */
    public class RedHerringInnerC {}

    /**
     * Link to member in outer class: {@link #MEMBER} <br/>
     * Link to member in inner class: {@link InnerC2#MEMBER2} <br/>
     * Link to another inner class: {@link InnerC2}
     */
    public class InnerC {}

    /**
     * Link to conflicting member in inner class: {@link #MEMBER} <br/>
     */
    public class InnerC2 {
        public static final int MEMBER = 1;
        public static final int MEMBER2 = 1;
    }

    public void method(InnerC p1, InnerC2 p2){}

}
