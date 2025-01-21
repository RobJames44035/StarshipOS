/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6843752 8192992
 * @summary missing code for an anti-dependent Phi in GCM
 *
 * @run main/othervm -Xbatch compiler.c2.Test6843752
 */

package compiler.c2;

public class Test6843752 {

    Item list;

    static class Item {
        public Item    next;
        public Item    prev;
        public boolean remove;

        Item(boolean r) { remove = r; }
    }

    private void linkIn(Item item) {
        Item head = list;
        if (head == null) {
            item.next = item;
            item.prev = item;
            list = item;
        } else {
            item.next = head;
            item.prev = head.prev;
            head.prev.next = item;
            head.prev = item;
        }
    }

    private void linkOut(Item item) {
        Item head = list;
        if (item.next == item) {
            list = null;
        } else {
            item.prev.next = item.next;
            item.next.prev = item.prev;
            if (head == item) {
                list = item.next;
            }
        }
        item.next = null;
        item.prev = null; // this is the null pointer we are seeing
    }

    private void removeItems(int numItems) {
        Item item = list;
        if (item == null) {
            return;
        }
        Item last = item.prev;
        boolean done = false;
        while (!done && numItems > 1) {
            // the original code "done = (item == last);" triggered an infinite loop
            // and was changed slightly in order to produce an exception instead.
            done = (item.next == last.next);
            item = item.next;
            if (item.prev.remove) {
                linkOut(item.prev);
            }
        }
    }

    public void perform(int numItems) {
        for (int i = 0; i < numItems; i++) {
            linkIn(new Item(i == 0));
        }
        removeItems(numItems);
        list = null;
    }

    static public void main(String[] args) {
        int caseCnt = 0;
        Test6843752 bj = new Test6843752();
        try {
            for (; caseCnt < 500000;) {
                int numItems = (++caseCnt % 2);
                if ((caseCnt % 64) == 0) {
                    numItems = 5;
                }
                bj.perform(numItems);
                if ((caseCnt % 100000) == 0) {
                    System.out.println("successfully performed " + caseCnt + " cases");
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: crashed during case " + caseCnt);
            e.printStackTrace(System.out);
            System.exit(97);
        }
    }
}

