/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8156101 8159935 8159122 8168615
 * @summary Tests for ExecutionControl SPI
 * @build KullaTesting ExecutionControlTestBase
 * @run testng UserExecutionControlTest
 */


import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeMethod;

@Test
public class UserExecutionControlTest extends ExecutionControlTestBase {

    @BeforeMethod
    @Override
    public void setUp() {
        setUp(builder -> builder.executionEngine("local"));
    }

    public void verifyLocal() throws ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        System.setProperty("LOCAL_CHECK", "TBD");
        assertEquals(System.getProperty("LOCAL_CHECK"), "TBD");
        assertEval("System.getProperty(\"LOCAL_CHECK\")", "\"TBD\"");
        assertEval("System.setProperty(\"LOCAL_CHECK\", \"local\")");
        assertEquals(System.getProperty("LOCAL_CHECK"), "local");
    }

}
