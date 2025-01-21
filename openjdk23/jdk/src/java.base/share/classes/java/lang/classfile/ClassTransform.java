/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile;

import java.lang.classfile.attribute.CodeAttribute;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import jdk.internal.classfile.impl.TransformImpl;

import static java.util.Objects.requireNonNull;

/**
 * A transformation on streams of {@link ClassElement}.
 *
 * @see ClassFileTransform
 *
 * @since 24
 */
@FunctionalInterface
public non-sealed interface ClassTransform
        extends ClassFileTransform<ClassTransform, ClassElement, ClassBuilder> {

    /**
     * A class transform that sends all elements to the builder.
     */
    static final ClassTransform ACCEPT_ALL = new ClassTransform() {
        @Override
        public void accept(ClassBuilder builder, ClassElement element) {
            builder.with(element);
        }
    };

    /**
     * Create a stateful class transform from a {@link Supplier}.  The supplier
     * will be invoked for each transformation.
     *
     * @param supplier a {@link Supplier} that produces a fresh transform object
     *                 for each traversal
     * @return the stateful class transform
     */
    static ClassTransform ofStateful(Supplier<ClassTransform> supplier) {
        return new TransformImpl.SupplierClassTransform(requireNonNull(supplier));
    }

    /**
     * Create a class transform that passes each element through to the builder,
     * and calls the specified function when transformation is complete.
     *
     * @param finisher the function to call when transformation is complete
     * @return the class transform
     */
    static ClassTransform endHandler(Consumer<ClassBuilder> finisher) {
        requireNonNull(finisher);
        return new ClassTransform() {
            @Override
            public void accept(ClassBuilder builder, ClassElement element) {
                builder.with(element);
            }

            @Override
            public void atEnd(ClassBuilder builder) {
                finisher.accept(builder);
            }
        };
    }

    /**
     * Create a class transform that passes each element through to the builder,
     * except for those that the supplied {@link Predicate} is true for.
     *
     * @param filter the predicate that determines which elements to drop
     * @return the class transform
     */
    static ClassTransform dropping(Predicate<ClassElement> filter) {
        requireNonNull(filter);
        return (b, e) -> {
            if (!filter.test(e))
                b.with(e);
        };
    }

    /**
     * Create a class transform that transforms {@link MethodModel} elements
     * with the supplied method transform.
     *
     * @param filter a predicate that determines which methods to transform
     * @param xform the method transform
     * @return the class transform
     */
    static ClassTransform transformingMethods(Predicate<MethodModel> filter,
                                              MethodTransform xform) {
        return new TransformImpl.ClassMethodTransform(requireNonNull(xform), requireNonNull(filter));
    }

    /**
     * Create a class transform that transforms {@link MethodModel} elements
     * with the supplied method transform.
     *
     * @param xform the method transform
     * @return the class transform
     */
    static ClassTransform transformingMethods(MethodTransform xform) {
        return transformingMethods(_ -> true, xform);
    }

    /**
     * Create a class transform that transforms the {@link CodeAttribute} (method body)
     * of {@link MethodModel} elements with the supplied code transform.
     *
     * @param filter a predicate that determines which methods to transform
     * @param xform the code transform
     * @return the class transform
     */
    static ClassTransform transformingMethodBodies(Predicate<MethodModel> filter,
                                                   CodeTransform xform) {
        return transformingMethods(filter, MethodTransform.transformingCode(xform));
    }

    /**
     * Create a class transform that transforms the {@link CodeAttribute} (method body)
     * of {@link MethodModel} elements with the supplied code transform.
     *
     * @param xform the code transform
     * @return the class transform
     */
    static ClassTransform transformingMethodBodies(CodeTransform xform) {
        return transformingMethods(MethodTransform.transformingCode(xform));
    }

    /**
     * Create a class transform that transforms {@link FieldModel} elements
     * with the supplied field transform.
     *
     * @param xform the field transform
     * @return the class transform
     */
    static ClassTransform transformingFields(FieldTransform xform) {
        return new TransformImpl.ClassFieldTransform(requireNonNull(xform), _ -> true);
    }

    /**
     * @implSpec
     * The default implementation returns this class transform chained with another
     * class transform from the argument. Chaining of two transforms requires to
     * involve a chained builder serving as a target builder for this transform
     * and also as a source of elements for the downstream transform.
     */
    @Override
    default ClassTransform andThen(ClassTransform t) {
        return new TransformImpl.ChainedClassTransform(this, requireNonNull(t));
    }
}
