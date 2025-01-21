/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6657619
 * @modules jdk.naming.dns
 * @summary DnsContext.debug is public static mutable (findbugs)
 * @author Vincent Ryan
 */

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/*
 * Check that the 'debug' class member is no longer publicly accessible.
 */
public class CheckAccess {
    public static final void main(String[] args) throws Exception {
        try {
            Class<?> clazz = Class.forName("com.sun.jndi.dns.DnsContext");
            Field field = clazz.getField("debug");
            if (Modifier.isPublic(field.getModifiers())) {
                throw new Exception(
                    "class member 'debug' must not be publicly accessible");
            }
        } catch (NoSuchFieldException e) {
            // 'debug' is not publicly accessible, ignore exception
        }
    }
}
