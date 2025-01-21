/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.functions.Return;
import jdk.test.lib.jittester.types.TypeKlass;

class ReturnFactory extends SafeFactory<Return> {
    private final long complexityLimit;
    private final int operatorLimit;
    private final Type resultType;
    private final boolean exceptionSafe;
    private final TypeKlass ownerClass;

    ReturnFactory(long compLimit, int opLimit, TypeKlass ownerClass,
            Type resultType, boolean exceptionSafe) {
        this.complexityLimit = compLimit;
        this.operatorLimit = opLimit;
        this.resultType = resultType;
        this.ownerClass = ownerClass;
        this.exceptionSafe = exceptionSafe;
    }

    @Override
    protected Return sproduce() throws ProductionFailedException {
        return new Return(new IRNodeBuilder().setComplexityLimit(complexityLimit - 1)
                .setOperatorLimit(operatorLimit - 1)
                .setOwnerKlass(ownerClass)
                .setResultType(resultType)
                .setExceptionSafe(exceptionSafe)
                .setNoConsts(false)
                .getLimitedExpressionFactory()
                .produce());
    }
}
