/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import javax.swing.*;
import java.util.LinkedList;

/*
 * @test
 * @summary Check that SpinnerListModel constructors and methods do not throw unexpected exceptions
 *          in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessSpinnerListModel
 */

public class HeadlessSpinnerListModel {
    public static void main(String args[]) {
        LinkedList list = new LinkedList();
        list.add("prev");
        list.add("this");
        list.add("next");
        SpinnerListModel m;
        m = new SpinnerListModel(list);
        m = new SpinnerListModel(new String[]{"prev", "this", "next"});
        m.getList();
        m.setList(list);
        m.setValue("next");
        m.getNextValue();
        m.getPreviousValue();
    }
}
