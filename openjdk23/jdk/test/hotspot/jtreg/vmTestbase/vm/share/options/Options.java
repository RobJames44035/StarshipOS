/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package vm.share.options;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * This annotation marks fields which should be scanned for @Option annotation,
 * see the souurce code of {@link vm.share.options.test.SimpleExampleWithOptionsAnnotation}
 * for detailed example.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Options {
        // default value for undefined attribute
        final public static String defPrefix = "[no prefix]";
        /**
         * The name of this group of option, added as prefix
         */
        String prefix() default defPrefix;
}
