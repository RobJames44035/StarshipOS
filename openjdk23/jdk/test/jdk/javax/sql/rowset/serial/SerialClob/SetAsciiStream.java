/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
import javax.sql.rowset.serial.SerialClob;
import javax.sql.rowset.serial.SerialException;

/**
 * @test
 * @bug 7077451
 * @summary tests if the correct exception is thrown when calling method setAsciiStream() on SerialClob
 */
public class SetAsciiStream {

    public static void main(String[] args) throws Exception {
        SerialClob clob = new SerialClob(new char[0]);
        try {
            clob.setAsciiStream(0);
        } catch (SerialException e) {
             System.out.println("Test PASSED");
        }
    }

}
