/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4862621
 * @summary generics: incorrect cyclic inheritance error
 *
 * @compile  CyclicInheritance5.java
 */

class G<N extends G.Node<N>> {
    static class Node<N extends Node<N>> {
    }
}
class F extends G<F.MyNode> {
    static class MyNode extends G.Node<MyNode> {
    }
}
