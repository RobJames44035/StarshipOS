/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import javax.swing.JComboBox;

/*
 * @test
 * @bug 8015336
 * @summary No NPE for BasicComboBoxEditor.setItem(null)
 * @author Sergey Malenkov
 */
public class Test8015336 {
    public static void main(String[] args) throws Exception {
        new JComboBox().getEditor().setItem(new Test8015336());
    }

    @Override
    public String toString() {
        return null;
    }
}
