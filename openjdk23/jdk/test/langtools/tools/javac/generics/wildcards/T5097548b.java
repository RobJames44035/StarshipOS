/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5097548
 * @summary Stack overflow in capture conversion
 * @author Peter von der Ah\u00e9
 * @compile  T5097548b.java
 */

interface Edge<N extends Node<? extends Edge<N>>> {
    void setEndNode(N n);
}
interface Node<E extends Edge<? extends Node<E>>> {
    E getOutEdge();
}

public class T5097548b {
    public static void main(String[] args) {
        Node<?> node = null;
        node.getOutEdge().setEndNode(null);
    }
}
