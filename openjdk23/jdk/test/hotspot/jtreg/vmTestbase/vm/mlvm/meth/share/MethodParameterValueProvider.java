/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.meth.share;

import java.lang.invoke.MethodType;

public interface MethodParameterValueProvider {

    Object getValue(MethodType t, int paramNum);

}
