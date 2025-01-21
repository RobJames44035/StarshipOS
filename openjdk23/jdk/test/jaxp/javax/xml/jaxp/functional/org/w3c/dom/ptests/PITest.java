/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */
package org.w3c.dom.ptests;

import static org.testng.Assert.assertEquals;
import static org.w3c.dom.ptests.DOMTestUtil.createDOMWithNS;

import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.ProcessingInstruction;

/*
 * @test
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm org.w3c.dom.ptests.PITest
 * @summary Test for the methods of Processing Instruction
 */
public class PITest {
    /*
     * Test getData, setData and getTarget methods
     */
    @Test
    public void test() throws Exception {
        Document document = createDOMWithNS("PITest01.xml");
        ProcessingInstruction pi = document.createProcessingInstruction("PI", "processing");
        assertEquals(pi.getData(), "processing");
        assertEquals(pi.getTarget(), "PI");

        pi.setData("newProcessing");
        assertEquals(pi.getData(), "newProcessing");
    }

}
