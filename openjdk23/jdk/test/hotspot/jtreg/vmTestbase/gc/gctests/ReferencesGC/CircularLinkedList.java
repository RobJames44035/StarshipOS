/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package gc.gctests.ReferencesGC;

class node {
    byte [] arr;
    node next;
    node prev;
    node(){ arr = new byte[100]; }
}

public class CircularLinkedList implements Cloneable {
    private node Root;

    public void addElement() {
       node newnode;

       newnode = new node();
       if (Root == null){
          Root = newnode;
          Root.next = Root;
          Root.prev = Root;
       } else{
          newnode.next = Root.next;
          Root.next.prev = newnode;
          Root.next = newnode;
          newnode.prev = Root;
       }
    }

    public void addNelements(int n) {
       for (int i = 0; i < n ; i++)
          addElement();
    }

    public int elementCount() {
       node p;
       int count;

       p = Root;
       count = 0;

       do {
          p = p.prev;
          count++;
       }while(p != Root);
       return count;
    }

    public Object clone() {
       node p;
       p = Root;

       if ( p == null ) return null;
       CircularLinkedList clone = new CircularLinkedList();
       do {
          clone.addElement();
          p = p.prev;
       } while(p != Root);
       return clone;
    }
}
