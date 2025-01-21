/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package test.rowset;

import java.util.Locale;
import java.sql.SQLException;
import javax.sql.rowset.RowSetProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import static org.testng.Assert.*;

/**
 * @test
 * @bug 8294989
 * @summary Check that the resource bundle can be accessed
 * @throws SQLException if an unexpected error occurs
 * @run testng/othervm
 */
public class ValidateResourceBundleAccess{
    // Expected JDBCResourceBundle message, jdbcrowsetimpl.invalstate
    private static final String INVALIDSTATE = "Invalid state";
    // Expected JDBCResourceBundle message, crsreader.connecterr
    private static final String RSREADERERROR = "Internal Error in RowSetReader: no connection or command";

    // Checking against English messages, set to US Locale
    @BeforeClass
    public void setEnglishEnvironment() {
        Locale.setDefault(Locale.US);
    }

    @Test
    public void testResourceBundleAccess() throws SQLException {
        var rsr = RowSetProvider.newFactory();
        var crs =rsr.createCachedRowSet();
        var jrs = rsr.createJdbcRowSet();
        // Simple test to force an Exception to validate the expected message
        // is found from the resource bundle
        try {
            jrs.getMetaData();
            throw new RuntimeException("$$$ Expected SQLException was not thrown!");
        } catch (SQLException sqe) {
            assertTrue(sqe.getMessage().equals(INVALIDSTATE));
        }
        // Now tests via CachedRowSet
        try {
            crs.execute();
            throw new RuntimeException("$$$ Expected SQLException was not thrown!");
        } catch (SQLException e) {
            assertTrue(e.getMessage().equals(RSREADERERROR));
        }
    }
}
