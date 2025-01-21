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

public class IgnoreXSITypeTest_C_A extends BaseTest {

    protected String getXMLDocument() {
        return "xsitype_C_A.xml";
    }

    protected String getSchemaFile() {
        return "base.xsd";
    }

    public IgnoreXSITypeTest_C_A(String name) {
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
    public void testDefaultDocument() {
        try {
            reset();
            validateDocument();
        } catch (Exception e) {
            fail("Validation failed: " + e.getMessage());
        }

        // default value of the feature is false
        checkFalseResult();
    }

    @Test
    public void testDefaultFragment() {
        try {
            reset();
            validateFragment();
        } catch (Exception e) {
            fail("Validation failed: " + e.getMessage());
        }

        // default value of the feature is false
        checkFalseResult();
    }

    @Test
    public void testSetFalseDocument() {
        try {
            reset();
            fValidator.setFeature(IGNORE_XSI_TYPE, false);
            validateDocument();
        } catch (Exception e) {
            fail("Validation failed: " + e.getMessage());
        }

        checkFalseResult();
    }

    @Test
    public void testSetFalseFragment() {
        try {
            reset();
            fValidator.setFeature(IGNORE_XSI_TYPE, false);
            validateFragment();
        } catch (Exception e) {
            fail("Validation failed: " + e.getMessage());
        }

        checkFalseResult();
    }

    @Test
    public void testSetTrueDocument() {
        try {
            reset();
            fValidator.setFeature(IGNORE_XSI_TYPE, true);
            validateDocument();
        } catch (Exception e) {
            fail("Validation failed: " + e.getMessage());
        }

        checkTrueResult();
    }

    @Test
    public void testSetTrueFragment() {
        try {
            reset();
            fValidator.setFeature(IGNORE_XSI_TYPE, true);
            validateFragment();
        } catch (Exception e) {
            fail("Validation failed: " + e.getMessage());
        }

        checkTrueResult();
    }

    private void checkTrueResult() {
        assertValidity(ItemPSVI.VALIDITY_NOTKNOWN, fRootNode.getValidity());
        assertValidationAttempted(ItemPSVI.VALIDATION_PARTIAL, fRootNode
                .getValidationAttempted());
        assertElementNull(fRootNode.getElementDeclaration());
        assertAnyType(fRootNode.getTypeDefinition());

        checkChild();
    }

    private void checkFalseResult() {
        assertValidity(ItemPSVI.VALIDITY_VALID, fRootNode.getValidity());
        assertValidationAttempted(ItemPSVI.VALIDATION_FULL, fRootNode
                .getValidationAttempted());
        assertElementNull(fRootNode.getElementDeclaration());
        assertTypeName("Y", fRootNode.getTypeDefinition().getName());
        assertTypeNamespaceNull(fRootNode.getTypeDefinition().getNamespace());

        checkChild();
    }

    private void checkChild() {
        PSVIElementNSImpl child = super.getChild(1);
        assertValidity(ItemPSVI.VALIDITY_VALID, child.getValidity());
        assertValidationAttempted(ItemPSVI.VALIDATION_FULL, child
                .getValidationAttempted());
        assertElementName("A", child.getElementDeclaration().getName());
        assertTypeName("Y", child.getTypeDefinition().getName());
        assertTypeNamespaceNull(child.getTypeDefinition().getNamespace());
    }
}
