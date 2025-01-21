/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
package test.rowset.jdbcrowset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import org.testng.annotations.Test;
import util.BaseTest;
import util.StubDriver;

public class JdbcRowSetDriverManagerTest extends BaseTest {

    // URL that the StubDriver recognizes
    private final static String StubDriverURL = "jdbc:tennis:boy";

    /**
     * Validate that JDBCRowSetImpl can connect to a JDBC driver that is
     * register by DriverManager.
     */
    @Test(enabled = true)
    public void test0000() throws SQLException {

        DriverManager.registerDriver(new StubDriver());

        // Show that the StubDriver is loaded and then call setAutoCommit on
        // the returned Connection
        dumpRegisteredDrivers();
        Connection con = DriverManager.getConnection(StubDriverURL, "userid", "password");
        con.setAutoCommit(true);

        // Have com.sun.rowset.JdbcRowSetImpl create a Connection and
        // then call setAutoCommit
        RowSetFactory rsf = RowSetProvider.newFactory();
        JdbcRowSet jrs = rsf.createJdbcRowSet();
        jrs.setUrl(StubDriverURL);
        jrs.setUsername("userid");
        jrs.setPassword("password");

        jrs.setAutoCommit(true);
    }

    private static void dumpRegisteredDrivers() {
        System.out.println("+++ Loaded Drivers +++");
        System.out.println("++++++++++++++++++++++++");
        DriverManager.drivers()
                .forEach(d
                        -> System.out.println("+++ Driver:" + d + " "
                        + d.getClass().getClassLoader()));
        System.out.println("++++++++++++++++++++++++");

    }
}
