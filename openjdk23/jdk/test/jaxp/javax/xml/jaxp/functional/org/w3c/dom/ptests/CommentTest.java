/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */
package org.w3c.dom.ptests;

import static org.w3c.dom.ptests.DOMTestUtil.createNewDocument;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/functional
 * @run testng/othervm org.w3c.dom.ptests.CommentTest
 * @summary Test for Comment implementation returned by Document.createComment(String)
 */
public class CommentTest extends AbstractCharacterDataTest {
    @Override
    protected CharacterData createCharacterData(String text) throws IOException, SAXException, ParserConfigurationException {
        Document document = createNewDocument();
        return document.createComment(text);
    }
}
