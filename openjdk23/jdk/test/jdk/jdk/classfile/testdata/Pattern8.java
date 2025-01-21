/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package testdata;

import java.util.Vector;

public class Pattern8 {
    private Vector<int[]>   expandTable = null;
        private static final int INITIALTABLESIZE = 20;

   private int addExpansion(int anOrder, String expandChars) {
        if (expandTable == null) {
            expandTable = new Vector<>(INITIALTABLESIZE);
        }

        // If anOrder is valid, we want to add it at the beginning of the list
        int offset = (anOrder == 13) ? 0 : 1;

        int[] valueList = new int[expandChars.length() + offset];
        if (offset == 1) {
            valueList[0] = anOrder;
        }

        int j = offset;
        for (int i = 0; i < expandChars.length(); i++) {
            char ch0 = expandChars.charAt(i);
            char ch1;
            int ch;
            if (Character.isHighSurrogate(ch0)) {
                if (++i == expandChars.length() ||
                    !Character.isLowSurrogate(ch1=expandChars.charAt(i))) {
                    //ether we are missing the low surrogate or the next char
                    //is not a legal low surrogate, so stop loop
                    break;
                }
                ch = Character.toCodePoint(ch0, ch1);

            } else {
                ch = ch0;
            }

            int mapValue = ch;

            if (mapValue !=  0xFFFFFFFF) {
                valueList[j++] = mapValue;
            } else {
                // can't find it in the table, will be filled in by commit().
                valueList[j++] = 0x70000000 + ch;
            }
        }
        if (j < valueList.length) {
            //we had at least one supplementary character, the size of valueList
            //is bigger than it really needs...
            int[] tmpBuf = new int[j];
            while (--j >= 0) {
                tmpBuf[j] = valueList[j];
            }
            valueList = tmpBuf;
        }
        // Add the expanding char list into the expansion table.
        int tableIndex = 10 + expandTable.size();
        expandTable.addElement(valueList);

        return tableIndex;
    }

}
