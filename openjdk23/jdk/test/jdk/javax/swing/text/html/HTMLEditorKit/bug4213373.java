/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import javax.swing.text.html.HTMLEditorKit;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * @test
 * @bug 4213373
 * @summary  Serialization bug on HTMLEditorKit.
 */

public class bug4213373 {

    public static void main(String[] args) throws Exception {
        HTMLEditorKit ekr = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HTMLEditorKit ekw = new HTMLEditorKit();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(ekw);
            byte[] buf = baos.toByteArray();

            ByteArrayInputStream bais = new ByteArrayInputStream(buf);
            ois = new ObjectInputStream(bais);
            ekr = (HTMLEditorKit) ois.readObject();
        } finally {
           if (oos != null) {
               oos.close();
           }
           if (ois != null) {
                ois.close();
            }
        }
    }
}
