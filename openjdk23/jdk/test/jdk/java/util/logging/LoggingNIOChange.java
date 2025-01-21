/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
   @test
   @bug 4948142
   @summary test NIO changes don't generate unchecked exceptions
   @run main LoggingNIOChange
*/
import java.util.logging.*;

public class LoggingNIOChange {
        public static void main(String args[]) throws Exception {
                ConsoleHandler console = new ConsoleHandler();
                XMLFormatter f = new XMLFormatter();
                try {
                console.setEncoding("junk");
                f.getHead(console);
                console.setEncoding(null);
                f.getHead(console);
                }catch (java.io.UnsupportedEncodingException e) {
                }
        }
}
