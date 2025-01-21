/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package vm.share.options;
import java.lang.annotation.*;
/**
 * This is an auxilary declaration for use with @Factory annotation,
 * allows to add a particular class to the factory.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FClass
{
//    final public static String def_default_value = "[no default]";
    /**
     * A key which tells the BasicObjectFactory to instaniate given class,
     * is mandatory.
     */
    String key(); // mandatory ;
    /**
     * Description of this kind of instances, is mandatory.
     */
    String description();

    /**
     * The class to instantiate, should have a default public constructor,
     * so that type().newInstance() will work, is mandatory.
     */
    Class<?> type();
}
