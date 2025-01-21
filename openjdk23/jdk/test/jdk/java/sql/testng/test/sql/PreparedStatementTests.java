/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package test.sql;

import org.testng.annotations.BeforeMethod;
import util.StubPreparedStatement;

public class PreparedStatementTests extends StatementTests {

    @BeforeMethod
    public void setUpMethod() throws Exception {
        stmt = new StubPreparedStatement();
    }

}
