/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 6270682
 * @summary Test the input stream is closed after loadtoXML returns.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

public class CloseXMLStream {
    public static void main(String[] args) throws Throwable {
        class ExpectedClosingException extends RuntimeException {};
        Properties props = new Properties();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        props.storeToXML(out, null);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray()) {
            public void close() {
                throw new ExpectedClosingException();
            }
        };
        try {
            props.loadFromXML(in);
            throw new Exception("Failed: loadFromXML does not close the is!");
        } catch (ExpectedClosingException ex) { /*OK*/ }
    }
}
