/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package test.transaction;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import javax.transaction.xa.XAException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import util.SerializedTransactionExceptions;

public class XAExceptionTests {

    protected final String reason = "reason";

    /**
     * Create XAException with no-arg constructor
     */
    @Test
    public void test1() {
        XAException ex = new XAException();
        assertTrue(ex.getMessage() == null
                && ex.getCause() == null
                && ex.errorCode == 0);
    }

    /**
     * Create XAException with message
     */
    @Test
    public void test2() {
        XAException ex = new XAException(reason);
        assertTrue(ex.getMessage().equals(reason)
                && ex.getCause() == null
                && ex.errorCode == 0);
    }

    /**
     * De-Serialize a XAException from JDBC 4.0 and make sure you can read it
     * back properly
     */
    @Test
    public void test3() throws Exception {

        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(SerializedTransactionExceptions.XAE_DATA));
        XAException ex = (XAException) ois.readObject();
        assertTrue(reason.equals(ex.getMessage())
                && ex.getCause() == null
                && ex.errorCode == 0);
    }

    /**
     * Create XAException specifying an error code
     */
    @Test
    public void test4() {
        int error = 21;
        XAException ex = new XAException(error);
        assertTrue(ex.getMessage() == null
                && ex.getCause() == null
                && ex.errorCode == error);
    }

    /**
     * Create XAException specifying an error code
     */
    @Test
    public void test5() {
        int error = 21;
        XAException ex = new XAException(error);
        ex.errorCode = error;
        assertTrue(ex.getMessage() == null
                && ex.getCause() == null
                && ex.errorCode == error);
    }

}
