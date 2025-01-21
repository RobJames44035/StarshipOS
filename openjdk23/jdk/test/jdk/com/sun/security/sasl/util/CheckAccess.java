/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/**
 * @test
 * @bug 6657695
 * @summary AbstractSaslImpl.logger is a static mutable (findbugs)
 * @author Vincent Ryan
 */

import java.lang.reflect.*;

/*
 * Check that the 'logger' class member is immutable.
 */
public class CheckAccess {
    public static final void main(String[] args) throws Exception {
            Class clazz =
                Class.forName("com.sun.security.sasl.util.AbstractSaslImpl");
            Field field = clazz.getDeclaredField("logger");
            if (! Modifier.isFinal(field.getModifiers())) {
                throw new Exception(
                    "class member 'logger' must be immutable");
            }
    }
}
