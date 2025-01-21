/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 4938846 8008577 8174269
 * @modules jdk.localedata
 * @summary Test case for en_IE TimeZone info
 * @run main Bug4938846
 */

import java.util.Locale;
import java.util.TimeZone;

public class Bug4938846 {

   public static void main(String[] args) {
       Locale tzLocale = Locale.of("en", "IE");

       TimeZone ieTz = TimeZone.getTimeZone("Europe/Dublin");

       // "Standard" because of the negative DST, summer is considered standard for Europe/Dublin
       if (!ieTz.getDisplayName(true, TimeZone.LONG, tzLocale).equals ("Irish Standard Time"))
             throw new RuntimeException("\nString for Europe/Dublin, en_IE locale should be \"Irish Standard Time\"");

   }
}
