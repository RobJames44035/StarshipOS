/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package jdk.vm.ci.runtime.test;

import jdk.vm.ci.meta.MetaAccessProvider;
import jdk.vm.ci.meta.ResolvedJavaMethod;
import jdk.vm.ci.meta.ResolvedJavaType;
import jdk.vm.ci.meta.Signature;
import jdk.vm.ci.runtime.JVMCI;

import java.lang.reflect.Method;
import java.util.Arrays;

class NameAndSignature {

    public static final MetaAccessProvider metaAccess = JVMCI.getRuntime().getHostJVMCIBackend().getMetaAccess();

    final String name;
    final Class<?> returnType;
    final Class<?>[] parameterTypes;

    NameAndSignature(Method m) {
        this.name = m.getName();
        this.returnType = m.getReturnType();
        this.parameterTypes = m.getParameterTypes();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NameAndSignature) {
            NameAndSignature s = (NameAndSignature) obj;
            return s.returnType == returnType && name.equals(s.name) && Arrays.equals(s.parameterTypes, parameterTypes);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + "(");
        String sep = "";
        for (Class<?> p : parameterTypes) {
            sb.append(sep);
            sep = ", ";
            sb.append(p.getName());
        }
        return sb.append(')').append(returnType.getName()).toString();
    }

    public boolean signatureEquals(ResolvedJavaMethod m) {
        Signature s = m.getSignature();
        ResolvedJavaType declaringClass = m.getDeclaringClass();
        if (!s.getReturnType(declaringClass).resolve(declaringClass).equals(metaAccess.lookupJavaType(returnType))) {
            return false;
        }
        if (s.getParameterCount(false) != parameterTypes.length) {
            return false;
        }
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!s.getParameterType(i, declaringClass).resolve(declaringClass).equals(metaAccess.lookupJavaType(parameterTypes[i]))) {
                return false;
            }
        }
        return true;
    }
}
