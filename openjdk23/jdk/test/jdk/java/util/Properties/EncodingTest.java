/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @test
 * @bug 8183743
 * @summary Test to verify the new overload method with Charset functions the
 * same as the existing method that takes a charset name.
 * @run testng EncodingTest
 */
public class EncodingTest {
    @DataProvider(name = "parameters")
    public Object[][] getParameters() throws IOException {
        return new Object[][]{
            {StandardCharsets.UTF_8.name(), null},
            {null, StandardCharsets.UTF_8},};
    }

    /**
     * Tests that properties saved with Properties#storeToXML with either an
     * encoding name or a charset can be read with Properties#loadFromXML that
     * returns the same Properties object.
     */
    @Test(dataProvider = "parameters")
    void testLoadAndStore(String encoding, Charset charset) throws IOException {
        Properties props = new Properties();
        props.put("k0", "\u6C34");
        props.put("k1", "foo");
        props.put("k2", "bar");
        props.put("k3", "\u0020\u0391\u0392\u0393\u0394\u0395\u0396\u0397");
        props.put("k4", "\u7532\u9aa8\u6587");
        props.put("k5", "<java.home>/conf/jaxp.properties");
        props.put("k6", "\ud800\u00fa");

        Properties p;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            if (encoding != null) {
                props.storeToXML(out, null, encoding);
            } else {
                props.storeToXML(out, null, charset);
            }   p = new Properties();
            try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
                p.loadFromXML(in);
            }
        }

        Assert.assertEquals(props, p);
    }
}
