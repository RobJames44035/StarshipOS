/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package pkg1;

import pkg.Coin;
import pkg.*;
import java.lang.annotation.*;

@Documented public @interface A {
    int i();
    double d();
    boolean b();
    String s();
    Class<?> c();
    Class<? extends TypeParameterSuperClass> w();
    Coin[] e();
    AnnotationType a();
    String[] sa();
    Class<?> primitiveClassTest();
    Class<?> arrayClassTest();
    Class<?> arrayPrimitiveTest();
    Class<?>[] classArrayTest();
}
