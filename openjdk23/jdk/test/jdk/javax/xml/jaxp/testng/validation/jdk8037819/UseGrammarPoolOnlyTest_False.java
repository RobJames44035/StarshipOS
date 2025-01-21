/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
package validation.jdk8037819;

import com.sun.org.apache.xerces.internal.xs.ItemPSVI;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import validation.BaseTest;

public class UseGrammarPoolOnlyTest_False extends BaseTest {
    private final static String UNKNOWN_TYPE_ERROR = "cvc-type.1";

    private final static String INVALID_DERIVATION_ERROR = "cvc-elt.4.3";

    protected String getXMLDocument() {
        return "otherNamespace.xml";
    }

    protected String getSchemaFile() {
        return "base.xsd";
    }

    protected String[] getRelevantErrorIDs() {
        return new String[] { UNKNOWN_TYPE_ERROR, INVALID_DERIVATION_ERROR };
    }

    protected boolean getUseGrammarPoolOnly() {
        return false;
    }

    public UseGrammarPoolOnlyTest_False(String name) {
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

    /**
     * The purpose of this test is to check if setting the USE_GRAMMAR_POOL_ONLY
     * feature to true causes external schemas to not be read. This
     * functionality already existed prior to adding the XSLT 2.0 validation
     * features; however, because the class that controlled it changed, this
     * test simply ensures that the existing functionality did not disappear.
     * -PM
     */
    @Test
    public void testUsingOnlyGrammarPool() {
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
        assertElementNamespace("xslt.unittests", fRootNode
                .getElementDeclaration().getNamespace());
        assertTypeName("W", fRootNode.getTypeDefinition().getName());
        assertTypeNamespace("xslt.unittests", fRootNode.getTypeDefinition()
                .getNamespace());
    }
}
