/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
package validation.jdk8037819;

import com.sun.org.apache.xerces.internal.dom.PSVIElementNSImpl;
import com.sun.org.apache.xerces.internal.xs.ItemPSVI;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import validation.BaseTest;

/**
 * The purpose of this test is to execute all of the isComparable calls in
 * XMLSchemaValidator. There are two calls in processElementContent and two
 * calls in processOneAttribute.
 *
 * @author peterjm
 */
public class FixedAttrTest extends BaseTest {

    protected String getXMLDocument() {
        return "fixedAttr.xml";
    }

    protected String getSchemaFile() {
        return "base.xsd";
    }

    public FixedAttrTest(String name) {
        super(name);
    }

    @BeforeClass
    protected void setUp() throws Exception {
        super.setUp();
    }

    @AfterClass
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testDefault() {
        try {
            reset();
            validateDocument();
        } catch (Exception e) {
            fail("Validation failed: " + e.getMessage());
        }

        assertValidity(ItemPSVI.VALIDITY_VALID, fRootNode.getValidity());
        assertValidationAttempted(ItemPSVI.VALIDATION_FULL, fRootNode
                .getValidationAttempted());
        assertElementName("A", fRootNode.getElementDeclaration().getName());

        PSVIElementNSImpl child = super.getChild(1);
        assertValidity(ItemPSVI.VALIDITY_VALID, child.getValidity());
        assertValidationAttempted(ItemPSVI.VALIDATION_FULL, child
                .getValidationAttempted());
        assertElementName("B", child.getElementDeclaration().getName());

        child = super.getChild(2);
        assertValidity(ItemPSVI.VALIDITY_VALID, child.getValidity());
        assertValidationAttempted(ItemPSVI.VALIDATION_FULL, child
                .getValidationAttempted());
        assertElementName("D", child.getElementDeclaration().getName());
    }
}
