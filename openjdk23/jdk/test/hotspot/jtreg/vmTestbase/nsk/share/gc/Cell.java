/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package nsk.share.gc;

import nsk.share.gc.MemoryObject;

public class Cell extends MemoryObject {
        private int number;

        public Cell(int size, int number) {
                super(size - 4);
                setNumber(number);
        }

        public final int getNumber() {
                return number;
        }

        public final void setNumber(int number) {
                this.number = number;
        }
}
