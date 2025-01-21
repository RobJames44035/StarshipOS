/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import vm.runtime.defmeth.shared.data.Interface;
import vm.runtime.defmeth.shared.data.InterfaceImpl;
import vm.runtime.defmeth.shared.data.method.Method;

/**
 * Builder for Interface instances.
 */
public class InterfaceBuilder extends ClassBuilder<InterfaceBuilder,Interface> {

    // Superinterfaces
    private List<Interface> parents = new ArrayList<>();

    /* package-private */ InterfaceBuilder(TestBuilder builder) {
        super(builder);
    }

    public InterfaceBuilder extend(Interface... intf) {
        parents.addAll(Arrays.asList(intf));

        return this;
    }

    public ClassMethodBuilder<InterfaceBuilder> defaultMethod(String name, String desc) {
        MethodBuilder mb = builder.method().name(name).desc(desc).type(MethodType.DEFAULT);
        return new ClassMethodBuilder<>(this, mb);
    }

    public ClassMethodBuilder<InterfaceBuilder> abstractMethod(String name, String desc) {
        MethodBuilder mb = builder.method().name(name).desc(desc).type(MethodType.ABSTRACT);
        return new ClassMethodBuilder<>(this, mb);
    }

    /**
     * Construct Data.Interface instance.
     * @return instance of Data.Interface class
     */
    @Override
    public Interface build() {
        if (name == null) {
            throw new IllegalStateException();
        }

        Interface intf = new InterfaceImpl(flags, name, majorVer, sig, parents.toArray(new Interface[0]),
                methods.toArray(new Method[0]));

        if (builder.hasElement(name)) {
            throw new IllegalStateException();
        }

        builder.register(intf);
        builder.finishConstruction(this);

        return intf;
    }
}
