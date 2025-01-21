/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package nsk.share.gc;

public class CircularLinkedList {
        private int objectSize;
        private LinkedMemoryObject root;

        /**
         * Create empty circular linked list.
         *
         * @param objectSize size of each node in the list
         */
        public CircularLinkedList(int objectSize) {
                this.objectSize = objectSize;
        }

        /**
         * Insert new node in the list.
         */
        public void grow() {
                LinkedMemoryObject newnode = new LinkedMemoryObject(objectSize);
                if (root == null){
                        root = newnode;
                        root.setNext(root);
                        root.setPrev(root);
                } else {
                        newnode.setNext(root.getNext());
                        root.getNext().setPrev(newnode);
                        root.setNext(newnode);
                        newnode.setPrev(root);
                }
        }

        /**
         * Get length of the list.
         *
         * @return length
         */
        public int getLength() {
                return Memory.getListLength(root);
        }

        /**
         * Get length of another list.
         *
         * @param list another list
         * @return length of list
         */
        public int getLength(CircularLinkedList list) {
                return Memory.getListLength(list.root);
        }
}
