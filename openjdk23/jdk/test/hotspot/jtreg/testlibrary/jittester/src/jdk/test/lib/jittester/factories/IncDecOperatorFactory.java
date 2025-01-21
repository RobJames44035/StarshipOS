/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.OperatorKind;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.UnaryOperator;
import jdk.test.lib.jittester.types.TypeKlass;

class IncDecOperatorFactory extends UnaryOperatorFactory {
    IncDecOperatorFactory(OperatorKind opKind, long complexityLimit, int operatorLimit,
                          Type klass, Type resultType, boolean safe, boolean noconsts) {
        super(opKind, complexityLimit, operatorLimit, klass, resultType, safe, noconsts);
    }

    @Override
    protected boolean isApplicable(Type resultType) {
        return TypeList.isBuiltInInt(resultType) && !resultType.equals(TypeList.BOOLEAN);
    }

    @Override
    protected UnaryOperator generateProduction(Type l) throws ProductionFailedException {
        return new UnaryOperator(opKind, new IRNodeBuilder().setComplexityLimit(complexityLimit - 1)
                .setOperatorLimit(operatorLimit - 1)
                .setOwnerKlass((TypeKlass) ownerClass)
                .setResultType(l)
                .setIsConstant(false)
                .setIsInitialized(true)
                .setExceptionSafe(exceptionSafe)
                .setNoConsts(exceptionSafe)
                .getVariableFactory()
                .produce());
    }
}
