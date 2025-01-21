/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package jdk.vm.ci.code;

import jdk.vm.ci.meta.JavaKind;
import jdk.vm.ci.meta.ValueKind;

/**
 * Can be implemented by compilers to create custom {@link ValueKind} subclasses.
 */
public interface ValueKindFactory<K extends ValueKind<K>> {

    K getValueKind(JavaKind javaKind);
}
