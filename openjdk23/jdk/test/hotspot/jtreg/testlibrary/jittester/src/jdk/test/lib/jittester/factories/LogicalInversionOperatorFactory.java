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

class LogicalInversionOperatorFactory extends UnaryOperatorFactory {
    LogicalInversionOperatorFactory(long complexityLimit, int operatorLimit,
            Type ownerType, Type resultType, boolean exceptionSafe, boolean noconsts) {
        super(OperatorKind.NOT, complexityLimit, operatorLimit, ownerType, resultType, exceptionSafe, noconsts);
    }

    @Override
    protected boolean isApplicable(Type resultType) {
        return resultType.equals(TypeList.BOOLEAN);
    }

    @Override
    protected UnaryOperator generateProduction(Type resultType) throws ProductionFailedException {
        return new UnaryOperator(opKind, new ExpressionFactory(complexityLimit - 1,
                operatorLimit - 1, (TypeKlass) ownerClass, resultType, exceptionSafe, noconsts).produce());
    }
}
