/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @summary test MessageFormat
 * @run junit MessageTest
 */
import java.util.*;
import java.io.*;
import java.text.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class MessageTest {


    @Test
   public void TestMSGPatternTest() {
        Object[] testArgs = {
             1D, 3456D,
            "Disk", new Date(10000000000L)};

        String[] testCases = {
           "Quotes '', '{', 'a' {0} '{0}'",
           "Quotes '', '{', 'a' {0,number} '{0}'",
           "'{'1,number,'#',##} {1,number,'#',##}",
           "There are {1} files on {2} at {3}",
           "On {2}, there are {1} files, with {0,number,currency}.",
           "'{1,number,percent}', {1,number,percent}, ",
           "'{1,date,full}', {1,date,full}, ",
           "'{3,date,full}', {3,date,full}, ",
           "'{1,number,#,##}' {1,number,#,##}",
        };

        for (int i = 0; i < testCases.length; ++i) {
            Locale save = Locale.getDefault();
            try {
                Locale.setDefault(Locale.US);
                System.out.println("");
                System.out.println( i + " Pat in:  " + testCases[i]);
                MessageFormat form = new MessageFormat(testCases[i]);
                System.out.println( i + " Pat out: " + form.toPattern());
                String result = form.format(testArgs);
                System.out.println( i + " Result:  " + result);
                Object[] values = form.parse(result);
                for (int j = 0; j < testArgs.length; ++j) {
                    Object testArg = testArgs[j];
                    Object value = null;
                    if (j < values.length) {
                        value = values[j];
                    }
                    if ((testArg == null && value != null)
                        || (testArg != null && !testArg.equals(value))) {
                       System.out.println( i + " " + j + " old: " + testArg);
                       System.out.println( i + " " + j + " new: " + value);
                    }
                }
            }
            catch(java.text.ParseException pe ) {
                throw new RuntimeException("Error: MessageFormat.parse throws ParseException");
            }
            finally{
                Locale.setDefault(save);
            }
        }
    }
}
