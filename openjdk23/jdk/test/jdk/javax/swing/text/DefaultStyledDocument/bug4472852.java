/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

import java.awt.Color;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/*
 * @test
 * @bug 4472852
 * @summary Tests DefaultStyledDocument.split(int, int)
 */

public class bug4472852 {

    public static void main(String[] args) throws Exception {
        // create a Document and insert some text there
        StyledDocument doc = new DefaultStyledDocument();
        doc.insertString(0, "this", null);

        // add style to the last word
        Element root = doc.getDefaultRootElement();
        int end = root.getEndOffset();
        MutableAttributeSet as = new SimpleAttributeSet();
        StyleConstants.setBackground(as, Color.BLUE);
        doc.setCharacterAttributes(end-5, 5, as, true);

        // inspect Elements of the only Paragraph --
        // there should be no empty Elements
        Element para = root.getElement(0);
        for (int i = 0; i < para.getElementCount(); i++) {
            Element el = para.getElement(i);
            if (el.getStartOffset() == el.getEndOffset()) {
                throw new RuntimeException("Failed: empty Element found");
            }
        }
    }
}
