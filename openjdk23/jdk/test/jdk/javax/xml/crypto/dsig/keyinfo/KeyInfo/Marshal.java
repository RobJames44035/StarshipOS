/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/**
 * @test
 * @bug 6372500
 * @summary Test that KeyInfo.marshal works correctly
 * @modules java.xml.crypto/org.jcp.xml.dsig.internal.dom
 * @author Sean Mullan
 */

import java.util.Collections;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.jcp.xml.dsig.internal.dom.DOMUtils;

public class Marshal {

    public static void main(String[] args) throws Exception {
        KeyInfoFactory fac = KeyInfoFactory.getInstance();
        KeyInfo ki = fac.newKeyInfo
            (Collections.singletonList(fac.newKeyName("foo")), "keyid");
        try {
            ki.marshal(null, null);
            throw new Exception("Should raise a NullPointerException");
        } catch (NullPointerException npe) {}

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = dbf.newDocumentBuilder().newDocument();
        Element elem = doc.createElementNS("http://acme.org", "parent");
        doc.appendChild(elem);
        DOMStructure parent = new DOMStructure(elem);
        ki.marshal(parent, null);

        Element kiElem = DOMUtils.getFirstChildElement(elem);
        if (!kiElem.getLocalName().equals("KeyInfo")) {
            throw new Exception
                ("Should be KeyInfo element: " + kiElem.getLocalName());
        }
        Element knElem = DOMUtils.getFirstChildElement(kiElem);
        if (!knElem.getLocalName().equals("KeyName")) {
            throw new Exception
                ("Should be KeyName element: " + knElem.getLocalName());
        }
    }
}
