/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6213818
 * @summary Compilercrash with NullPointerException at SubstFcn.subst(Types.java:2057)
 * @compile T6213818.java
 */

public class T6213818 {
    static interface Edge<N extends Node<? extends Edge<N>>> { }
    static interface Node<E extends Edge<? extends Node<E>>> { }
    static class BasicNode<E extends BasicEdge<N, E> & Edge<N>, N extends BasicNode<E, N> & Node<E>> implements Node<E> { }
    static class BasicEdge<N extends BasicNode<E, N> & Node<E>, E extends BasicEdge<N, E> & Edge<N>> implements Edge<N> { }
}
