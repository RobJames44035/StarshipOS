/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.OperatorKind;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.utils.TypeUtil;
import jdk.test.lib.jittester.UnaryOperator;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.utils.PseudoRandom;

class BitwiseInversionOperatorFactory extends UnaryOperatorFactory {
    BitwiseInversionOperatorFactory(long complexityLimit, int operatorLimit, Type ownerClass,
            Type resultType, boolean exceptionSafe, boolean noconsts) {
        super(OperatorKind.BIT_NOT, complexityLimit, operatorLimit, ownerClass, resultType, exceptionSafe, noconsts);
    }

    @Override
    protected boolean isApplicable(Type resultType) {
        return resultType.equals(TypeList.INT) || resultType.equals(TypeList.LONG);
    }

    @Override
    protected Type generateType() {
        if (resultType.equals(TypeList.INT)) {
            return PseudoRandom.randomElement(TypeUtil.getImplicitlyCastable(TypeList.getBuiltIn(), resultType));
        } else {
            return resultType;
        }
    }

    @Override
    protected UnaryOperator generateProduction(Type resultType) throws ProductionFailedException {
        return new UnaryOperator(opKind, new IRNodeBuilder().setComplexityLimit(complexityLimit - 1)
                .setOperatorLimit(operatorLimit - 1)
                .setOwnerKlass((TypeKlass) ownerClass)
                .setResultType(resultType)
                .setExceptionSafe(exceptionSafe)
                .setNoConsts(noconsts)
                .getExpressionFactory()
                .produce());
    }
}
