/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */


/*
   @test
   @bug 4247487
   @summary Tests that the following methods of JTable are public:
            int getAccessibleColumnAtIndex(int)
            int getAccessibleRowAtIndex(int)
            int getAccessibleIndexAt(int, int)
*/

import javax.swing.JTable;

public class bug4247487 {

    static class TestTable extends JTable {

        public TestTable() {
            super(new Object[][]{{"one", "two"}},
                  new Object[]{"A", "B"});
        }

        public void test() {
            int[] rowIndices = {0, 0, 1, 1};
            int[] colIndices = {0, 1, 0, 1};
            JTable.AccessibleJTable at =
                (JTable.AccessibleJTable)getAccessibleContext();

            for (int i=0; i<4; i++) {
                if (at.getAccessibleRowAtIndex(i) != rowIndices[i]) {
                    throw new Error("Failed: wrong row index");
                }
                if (at.getAccessibleColumnAtIndex(i) != colIndices[i]) {
                    throw new Error("Failed: wrong column index");
                }
            }
            if (at.getAccessibleIndexAt(0,0) != 0 ||
                at.getAccessibleIndexAt(0,1) != 1 ||
                at.getAccessibleIndexAt(1,0) != 2 ||
                at.getAccessibleIndexAt(1,1) != 3) {

                throw new Error("Failed: wrong index");
            }
        }
    }

    public static void main(String[] argv) {
        TestTable test = new TestTable();
        test.test();
    }
}
