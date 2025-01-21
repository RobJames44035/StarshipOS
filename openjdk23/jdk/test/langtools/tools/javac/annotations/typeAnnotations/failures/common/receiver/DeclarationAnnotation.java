/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

class DeclarationAnnotation {
    void bad(@DA DeclarationAnnotation this) {}
    void good(@TA DeclarationAnnotation this) {}
}

@interface DA { }

@Target(ElementType.TYPE_USE)
@interface TA { }
