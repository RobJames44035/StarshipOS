/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package jdk.vm.ci.meta;

import java.lang.reflect.Modifier;

import static java.lang.reflect.Modifier.PRIVATE;
import static java.lang.reflect.Modifier.PROTECTED;
import static java.lang.reflect.Modifier.PUBLIC;

/**
 * A Java element (i.e., a class, interface, field or method) that is described by a set of Java
 * language {@linkplain #getModifiers() modifiers}.
 */
public interface ModifiersProvider {

    /**
     * Returns the modifiers for this element.
     */
    int getModifiers();

    /**
     * @see Modifier#isInterface(int)
     */
    default boolean isInterface() {
        return Modifier.isInterface(getModifiers());
    }

    /**
     * @see Modifier#isSynchronized(int)
     */
    default boolean isSynchronized() {
        return Modifier.isSynchronized(getModifiers());
    }

    /**
     * @see Modifier#isStatic(int)
     */
    default boolean isStatic() {
        return Modifier.isStatic(getModifiers());
    }

    /**
     * The setting of the final modifier bit for types is somewhat confusing, so don't export
     * isFinal by default. Subclasses like {@link ResolvedJavaField} and {@link ResolvedJavaMethod}
     * can export it as isFinal, but {@link ResolvedJavaType} can provide a more sensible equivalent
     * like {@link ResolvedJavaType#isLeaf}.
     *
     * @see Modifier#isFinal(int)
     */
    default boolean isFinalFlagSet() {
        return Modifier.isFinal(getModifiers());
    }

    /**
     * @see Modifier#isPublic(int)
     */
    default boolean isPublic() {
        return Modifier.isPublic(getModifiers());
    }

    /**
     * Determines if this element is neither {@linkplain #isPublic() public},
     * {@linkplain #isProtected() protected} nor {@linkplain #isPrivate() private}.
     */
    default boolean isPackagePrivate() {
        return ((PUBLIC | PROTECTED | PRIVATE) & getModifiers()) == 0;
    }

    /**
     * @see Modifier#isPrivate(int)
     */
    default boolean isPrivate() {
        return Modifier.isPrivate(getModifiers());
    }

    /**
     * @see Modifier#isProtected(int)
     */
    default boolean isProtected() {
        return Modifier.isProtected(getModifiers());
    }

    /**
     * @see Modifier#isTransient(int)
     */
    default boolean isTransient() {
        return Modifier.isTransient(getModifiers());
    }

    /**
     * @see Modifier#isStrict(int)
     */
    default boolean isStrict() {
        return Modifier.isStrict(getModifiers());
    }

    /**
     * @see Modifier#isVolatile(int)
     */
    default boolean isVolatile() {
        return Modifier.isVolatile(getModifiers());
    }

    /**
     * @see Modifier#isNative(int)
     */
    default boolean isNative() {
        return Modifier.isNative(getModifiers());
    }

    /**
     * @see Modifier#isAbstract(int)
     */
    default boolean isAbstract() {
        return Modifier.isAbstract(getModifiers());
    }

    /**
     * Returns true if this element is a method with a concrete implementation, or a type that can
     * be instantiated. For example, array types return true for both {@link #isAbstract()} and this
     * method.
     *
     * @see ResolvedJavaType#isConcrete()
     */
    default boolean isConcrete() {
        return !isAbstract();
    }
}
