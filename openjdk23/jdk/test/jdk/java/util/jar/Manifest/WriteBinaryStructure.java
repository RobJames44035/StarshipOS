/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.Manifest;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @test
 * @bug 8066619
 * @run testng WriteBinaryStructure
 * @summary Tests that jar manifests are written in a particular structure
 */
public class WriteBinaryStructure {

    @Test
    public void testMainAttributes() throws IOException {
        Manifest mf = new Manifest();
        mf.getMainAttributes().put(Name.MANIFEST_VERSION, "1.0");
        mf.getMainAttributes().put(new Name("Key"), "Value");
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        mf.write(buf);
        assertEquals(buf.toByteArray(), (
                "Manifest-Version: 1.0\r\n" +
                "Key: Value\r\n" +
                "\r\n").getBytes(UTF_8));
    }

    @Test
    public void testIndividualSection() throws IOException {
        Manifest mf = new Manifest();
        mf.getMainAttributes().put(Name.MANIFEST_VERSION, "1.0");
        Attributes attributes = new Attributes();
        mf.getEntries().put("Individual-Section-Name", attributes);
        attributes.put(new Name("Key"), "Value");
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        mf.write(buf);
        assertEquals(buf.toByteArray(), (
                "Manifest-Version: 1.0\r\n" +
                "\r\n" +
                "Name: Individual-Section-Name\r\n" +
                "Key: Value\r\n" +
                "\r\n").getBytes(UTF_8));
    }

}
