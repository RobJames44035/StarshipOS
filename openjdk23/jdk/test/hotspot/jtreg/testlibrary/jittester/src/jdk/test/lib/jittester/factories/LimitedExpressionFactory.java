/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.ProductionLimiter;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.types.TypeKlass;

class LimitedExpressionFactory extends ExpressionFactory {
    LimitedExpressionFactory(long complexityLimit, int operatorLimit, TypeKlass ownerClass,
            Type resultType, boolean exceptionSafe, boolean noconsts) throws ProductionFailedException {
        super(complexityLimit, operatorLimit, ownerClass, resultType, exceptionSafe, noconsts);
    }

    @Override
    public IRNode sproduce() throws ProductionFailedException {
        ProductionLimiter.setLimit();
        try {
            return super.sproduce();
        } finally {
            ProductionLimiter.setUnlimited();
        }
    }
}
