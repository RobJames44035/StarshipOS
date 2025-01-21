/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @run testng DescribeResolveTest
 */

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.constant.Constable;
import java.lang.constant.ConstantDesc;
import java.lang.invoke.MethodHandles;

import static org.testng.Assert.assertEquals;

public class DescribeResolveTest {

    @DataProvider
    public static Object[][] constables() {
        return new Object[][]{
            { true },
            { false },
            { (short) 10 },
            { (byte) 10 },
            { (char) 10 },
        };
    }

    @Test(dataProvider = "constables")
    public void testDescribeResolve(Constable constable) throws ReflectiveOperationException {
        ConstantDesc desc = constable.describeConstable().orElseThrow();
        Object resolved = desc.resolveConstantDesc(MethodHandles.lookup());
        assertEquals(constable, resolved);
    }

}
