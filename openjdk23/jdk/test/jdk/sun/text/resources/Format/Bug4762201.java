/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 4762201 8174269
 * @modules jdk.localedata
 * @summary verify the zh_CN full time pattern (and other time patterns)
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Bug4762201
{
        public static void main(String[] arg)
        {
                int result = 0;
                Locale loc = Locale.of("zh","CN");
                Date now = new Date();

                DateFormat df =
                   DateFormat.getTimeInstance(DateFormat.SHORT,loc);
                SimpleDateFormat sdf = new SimpleDateFormat("",loc);
                sdf.applyPattern("HH:mm");                              // short time pattern
                if( !sdf.format(now).equals(df.format(now))) result++;
                df =  DateFormat.getTimeInstance(DateFormat.MEDIUM,loc);
                sdf.applyPattern("HH:mm:ss");                           // medium time pattern
                if( !sdf.format(now).equals(df.format(now))) result++;
                df = DateFormat.getTimeInstance(DateFormat.LONG,loc);
                sdf.applyPattern("z HH:mm:ss");                         // long time pattern
                if( !sdf.format(now).equals(df.format(now))) result++;
                df = DateFormat.getTimeInstance(DateFormat.FULL,loc);
                sdf.applyPattern("zzzz HH:mm:ss");                      // full time pattern
                if( !sdf.format(now).equals(df.format(now))) result++;

           if(result > 0) throw new RuntimeException();
        }
}
