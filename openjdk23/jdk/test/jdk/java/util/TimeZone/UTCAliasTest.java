/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6282072
 * @summary Make sure that "UTC" is an alias of "Etc/UTC" as defined in the tzdata backward.
 * @modules java.base/sun.util.calendar
 * @compile -XDignore.symbol.file UTCAliasTest.java
 * @run main UTCAliasTest
 */

import java.util.*;
import sun.util.calendar.ZoneInfo;

public class UTCAliasTest {
    public static void main(String[] args) {
        Map<String,String> map = ZoneInfo.getAliasTable();
        String alias = map.get("UTC");
        if (!alias.equals("Etc/UTC")) {
            throw new RuntimeException("got " + alias + ", expected Etc/UTC");
        }
        TimeZone GMT = TimeZone.getTimeZone("GMT");
        TimeZone UTC = TimeZone.getTimeZone("UTC");
        if (!GMT.hasSameRules(UTC)) {
            throw new RuntimeException("GMT and UTC have different rules");
        }
        TimeZone EtcUTC = TimeZone.getTimeZone("Etc/UTC");
        if (!UTC.hasSameRules(EtcUTC)) {
            throw new RuntimeException("UTC and Etc/UTC have different rules");
        }
    }
}
