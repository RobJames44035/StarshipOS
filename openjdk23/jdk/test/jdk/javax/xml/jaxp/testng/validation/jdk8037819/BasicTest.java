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

public class BasicTest extends BaseTest {

    protected String getXMLDocument() {
        return "base.xml";
    }

    protected String getSchemaFile() {
        return "base.xsd";
    }

    public BasicTest(String name) {
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
    public void testSimpleValidation() {
        try {
            reset();
            validateDocument();
        } catch (Exception e) {
            fail("Validation failed: " + e.getMessage());
        }
        doValidityAsserts();
    }

    @Test
    public void testSimpleValidationWithTrivialXSIType() {
        try {
            reset();
            ((PSVIElementNSImpl) fRootNode).setAttributeNS(
                "http://www.w3.org/2001/XMLSchema-instance", "type", "X");
            validateDocument();
        } catch (Exception e) {
            fail("Validation failed: " + e.getMessage());
        }
        doValidityAsserts();
    }

    private void doValidityAsserts() {
        assertValidity(ItemPSVI.VALIDITY_VALID, fRootNode.getValidity());
        assertValidationAttempted(ItemPSVI.VALIDATION_FULL, fRootNode
                .getValidationAttempted());
        assertElementName("A", fRootNode.getElementDeclaration().getName());
        assertElementNamespaceNull(fRootNode.getElementDeclaration()
                .getNamespace());
        assertTypeName("X", fRootNode.getTypeDefinition().getName());
        assertTypeNamespaceNull(fRootNode.getTypeDefinition().getNamespace());
    }
}
