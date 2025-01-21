/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @summary Make sure that ResourceBundle.getBundle ignores LinkageError for compatibility.
 * @bug 6572242
 */

import java.util.ResourceBundle;

public class Bug6572242 {
    public static void main(String[] args) {
        ResourceBundle rb = ResourceBundle.getBundle("bug6572242");
        String data = rb.getString("data");
        if (!data.equals("type")) {
            throw new RuntimeException("got \"" + data + "\", expected \"type\"");
        }
    }
}
