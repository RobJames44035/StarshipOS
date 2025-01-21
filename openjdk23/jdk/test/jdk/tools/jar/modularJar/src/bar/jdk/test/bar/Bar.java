/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.bar;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Exports;
import java.lang.module.ModuleDescriptor.Provides;
import java.lang.module.ModuleReference;
import java.lang.module.ResolvedModule;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.HashSet;
import java.util.Set;

import jdk.internal.module.ModuleHashes;
import jdk.internal.module.ModuleReferenceImpl;
import jdk.test.bar.internal.Message;

public class Bar {
    public static void main(String[] args) throws Exception {
        System.out.println("message:" + Message.get());

        ModuleDescriptor md = Bar.class.getModule().getDescriptor();
        System.out.println("nameAndVersion:" + md.toNameAndVersion());
        md.mainClass().ifPresent(mc -> System.out.println("mainClass:" + mc));

        StringJoiner sj = new StringJoiner(",");
        md.requires().stream().map(ModuleDescriptor.Requires::name).sorted().forEach(sj::add);
        System.out.println("requires:" + sj.toString());

        sj = new StringJoiner(",");
        md.exports().stream().map(ModuleDescriptor.Exports::source).sorted().forEach(sj::add);
        if (!sj.toString().equals(""))
            System.out.println("exports:" + sj.toString());

        sj = new StringJoiner(",");
        md.uses().stream().sorted().forEach(sj::add);
        if (!sj.toString().equals(""))
            System.out.println("uses:" + sj.toString());

        sj = new StringJoiner(",");
        md.provides().stream().map(Provides::service).sorted().forEach(sj::add);
        if (!sj.toString().equals(""))
            System.out.println("provides:" + sj.toString());

        sj = new StringJoiner(",");
        Set<String> concealed = new HashSet<>(md.packages());
        md.exports().stream().map(Exports::source).forEach(concealed::remove);
        concealed.forEach(sj::add);
        if (!sj.toString().equals(""))
            System.out.println("contains:" + sj.toString());


        Module foo = jdk.test.foo.Foo.class.getModule();
        Optional<ResolvedModule> om = foo.getLayer().configuration().findModule(foo.getName());
        assert om.isPresent();
        ModuleReference mref = om.get().reference();
        assert mref instanceof ModuleReferenceImpl;
        ModuleHashes hashes = ((ModuleReferenceImpl) mref).recordedHashes();
        assert hashes != null;
        System.out.println("hashes:" + hashes.hashFor("bar"));
    }
}
