/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
import javax.sql.rowset.serial.SerialClob;
import javax.sql.rowset.serial.SerialException;

/**
 * @test
 * @bug 7077451
 * @summary tests if the correct exception is thrown when calling method setCharacterStream() on SerialClob
 */
public class SetCharacterStream {

    public static void main(String[] args) throws Exception {
        SerialClob clob = new SerialClob(new char[0]);
        try {
            clob.setCharacterStream(0);
        } catch (SerialException e) {
             System.out.println("Test PASSED");
        }
    }

}
