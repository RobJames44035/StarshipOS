/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.data;

import vm.runtime.defmeth.shared.builder.TestBuilder;

/**
 * Wrapper around some Interface instance. It delegates all calls to
 * underlying instance. Lazy resolution of a target instance by it's name
 * allows to delay resolution till runtime and "tie-the-knot" in recursive
 * hierarchies.
 */
public class InterfaceLazyAdapter extends ClazzLazyAdapter implements Interface {

    private final TestBuilder builder;
    private final String name;

    private Interface target;

    public InterfaceLazyAdapter(TestBuilder builder, String name) {
        super(builder, name);

        this.builder = builder;
        this.name = name;
    }

    private Interface delegate() {
        if (target == null) {
            target = (Interface)builder.lookup(name);
        }

        return target;
    }

    @Override
    public Interface[] parents() {
        return delegate().parents();
    }

    @Override
    public void visit(Visitor v) {
        delegate().visit(v);
    }
}
