/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

import javax.swing.JInternalFrame;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/*
 * @test
 * @bug 4308938
 * @summary Tests if Serializing JInternalFrame throws Error
 */

public class bug4308938 {
    private static JInternalFrame jif =
            new JInternalFrame("Serializable",true,true,true,true);

    public static void main(String[] args) throws Exception {
        try {
            jif.setLocation(100,100);
            jif.setSize(100,100);
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            ObjectOutputStream o = new ObjectOutputStream(s);
            o.writeObject(jif);
            o.close();
            o = new ObjectOutputStream(s);
            o.writeObject(jif);
            o.close();
        } catch (Exception e) {
            throw new RuntimeException("Serializing JInternalFrame throws Error");
        } finally {
            if (jif != null) {
                jif.dispose();
            }
        }
    }
}
