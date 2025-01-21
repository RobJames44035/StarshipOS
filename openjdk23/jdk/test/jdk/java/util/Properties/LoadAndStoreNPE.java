/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

/*
 * @test
 * @bug 8073214 8075362
 * @summary Tests to verify that load() and store() throw NPEs as advertised.
 */
public class LoadAndStoreNPE
{
    public static void main(String[] args) throws Exception
    {
        int failures = 0;

        Properties props = new Properties();

        try {
            props.store((OutputStream)null, "comments");
            failures++;
        } catch (NullPointerException e) {
            // do nothing
        }

        try {
            props.store((Writer)null, "comments");
            failures++;
        } catch (NullPointerException e) {
            // do nothing
        }

        try {
            props.load((InputStream)null);
            failures++;
        } catch (NullPointerException e) {
            // do nothing
        }

        try {
            props.load((Reader)null);
            failures++;
        } catch (NullPointerException e) {
            // do nothing
        }

        if (failures != 0) {
            throw new RuntimeException("LoadAndStoreNPE failed with "
                + failures + " errors!");
        }
    }
}
