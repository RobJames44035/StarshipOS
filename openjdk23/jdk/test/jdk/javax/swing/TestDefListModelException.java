/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
/*
 * @test
 * @bug 6187113
 * @summary  Verifies if
 *  DefaultListSelectionModel.removeIndexInterval(0, Integer.MAX_VALUE) fails
 * @run main TestDefListModelException
 */
import javax.swing.DefaultListSelectionModel;

public class TestDefListModelException {

    public static void main(String[] args) throws Exception {
        test1();
        test2();
        test3();
        test4();
    }

    private static void test1() {
        DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionInterval(0, 1);
        selectionModel.removeIndexInterval(0, Integer.MAX_VALUE);
    }

    private static void test2() {
        DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionInterval(Integer.MAX_VALUE - 1, Integer.MAX_VALUE);
        selectionModel.removeIndexInterval(0, 1);
    }

    private static void test3() {
        DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionInterval(Integer.MAX_VALUE - 2, Integer.MAX_VALUE - 1);
        selectionModel.removeIndexInterval(0, Integer.MAX_VALUE - 1);
    }

    private static void test4() {
        DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionInterval(Integer.MAX_VALUE - 1, Integer.MAX_VALUE);
        selectionModel.insertIndexInterval(Integer.MAX_VALUE - 1, Integer.MAX_VALUE, true);
    }
}

