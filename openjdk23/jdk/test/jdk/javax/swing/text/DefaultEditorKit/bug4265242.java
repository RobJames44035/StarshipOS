/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JTextPane;
import javax.swing.text.DefaultEditorKit;

/*
 * @test
 * @bug 4265242
 * @summary Tests endParagraphAction in JTextPane
 */

public class bug4265242 {
    public static void main(String[] args) {
        JTextPane jTextPane = new JTextPane();
        jTextPane.setText("Merry sparrow");

        Action[] actions = jTextPane.getActions();
        Action endPara = null;
        for (Action action : actions) {
            String name = (String) action.getValue(Action.NAME);
            if (name.equals(DefaultEditorKit.endParagraphAction)) {
                endPara = action;
            }
        }
        endPara.actionPerformed(new ActionEvent(jTextPane,
                ActionEvent.ACTION_PERFORMED,
                DefaultEditorKit.endParagraphAction));
    }
}
