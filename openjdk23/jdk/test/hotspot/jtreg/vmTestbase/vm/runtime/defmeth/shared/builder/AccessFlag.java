/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.builder;

import static org.objectweb.asm.Opcodes.*;

/**
 * Access flags, related to element visibility (public, private, etc).
 *
 * Introduced to simplify management of access flags due to peculiarity how
 * package-private is represented ((ACC_PUBLIC | ACC_PROTECTED | ACC_PRIVATE) == 0).
 */
public enum AccessFlag {
    PUBLIC          (ACC_PUBLIC),
    PROTECTED       (ACC_PROTECTED),
    PRIVATE         (ACC_PRIVATE),
    PACKAGE_PRIVATE (0);

    public final int mask;

    AccessFlag(int mask) {
        this.mask = mask;
    }
}
