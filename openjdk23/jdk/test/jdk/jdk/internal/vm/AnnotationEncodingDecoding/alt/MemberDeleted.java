/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package jdk.internal.vm.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MemberDeleted {
    String value();
    int retained();
}
