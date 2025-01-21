/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

// key: compiler.err.expression.not.allowable.as.annotation.value

import java.util.function.*;

@interface Anno {
    String value();
}

@Anno(value = AnnotationMustBeConstant.m(x -> x))
class AnnotationMustBeConstant {
    static String m(Function<String, String> f) {
        return null;
    }
}
