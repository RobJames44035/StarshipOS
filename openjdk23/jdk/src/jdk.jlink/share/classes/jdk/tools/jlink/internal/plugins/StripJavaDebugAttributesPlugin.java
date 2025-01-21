/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package jdk.tools.jlink.internal.plugins;

import java.util.function.Predicate;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassTransform;
import java.lang.classfile.CodeTransform;
import java.lang.classfile.MethodTransform;
import java.lang.classfile.attribute.MethodParametersAttribute;
import java.lang.classfile.attribute.SourceFileAttribute;
import java.lang.classfile.attribute.SourceDebugExtensionAttribute;

import jdk.tools.jlink.plugin.ResourcePool;
import jdk.tools.jlink.plugin.ResourcePoolBuilder;
import jdk.tools.jlink.plugin.ResourcePoolEntry;

/**
 *
 * Strip java debug attributes plugin
 */
public final class StripJavaDebugAttributesPlugin extends AbstractPlugin {
    private final Predicate<String> predicate;

    public StripJavaDebugAttributesPlugin() {
        this((path) -> false);
    }

    StripJavaDebugAttributesPlugin(Predicate<String> predicate) {
        super("strip-java-debug-attributes");
        this.predicate = predicate;
    }

    @Override
    public ResourcePool transform(ResourcePool in, ResourcePoolBuilder out) {
        //remove *.diz files as well as debug attributes.
        in.transformAndCopy((resource) -> {
            ResourcePoolEntry res = resource;
            if (resource.type().equals(ResourcePoolEntry.Type.CLASS_OR_RESOURCE)) {
                String path = resource.path();
                if (path.endsWith(".class")) {
                    if (path.endsWith("module-info.class")) {
                        // XXX. Do we have debug info?
                    } else {
                        var clm = newClassReader(path, resource,
                                ClassFile.DebugElementsOption.DROP_DEBUG,
                                ClassFile.LineNumbersOption.DROP_LINE_NUMBERS);
                        byte[] content = ClassFile.of().transformClass(clm, ClassTransform
                                        .dropping(cle -> cle instanceof SourceFileAttribute
                                                            || cle instanceof SourceDebugExtensionAttribute)
                                              .andThen(ClassTransform.transformingMethods(MethodTransform
                                                    .dropping(me -> me instanceof MethodParametersAttribute)
                                                    .andThen(MethodTransform
                                                            .transformingCode(CodeTransform.ACCEPT_ALL)))));
                        res = resource.copyWithContent(content);
                    }
                }
            } else if (predicate.test(res.path())) {
                res = null;
            }
            return res;
        }, out);

        return out.build();
    }
}
