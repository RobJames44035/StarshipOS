/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package sun.management;

import java.lang.management.ManagementPermission;
import java.util.List;
import javax.management.ObjectName;
import javax.management.MalformedObjectNameException;

public class Util {
    private Util() {}  // there are no instances of this class

    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    static String[] toStringArray(List<String> list) {
        return list.toArray(EMPTY_STRING_ARRAY);
    }

    public static ObjectName newObjectName(String domainAndType, String name) {
        return newObjectName(domainAndType + ",name=" + name);
    }

    public static ObjectName newObjectName(String name) {
        try {
            return ObjectName.getInstance(name);
        } catch (MalformedObjectNameException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
