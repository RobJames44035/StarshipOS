/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import java.util.ArrayList;
import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.Literal;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.ProductionParams;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.arrays.ArrayCreation;
import jdk.test.lib.jittester.arrays.ArrayExtraction;
import jdk.test.lib.jittester.types.TypeArray;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.utils.PseudoRandom;

class ArrayExtractionFactory extends SafeFactory<ArrayExtraction> {
    private final long complexityLimit;
    private final int operatorLimit;
    private final Type resultType;
    private final TypeKlass ownerClass;
    private final boolean exceptionSafe;
    private final boolean noconsts;

    ArrayExtractionFactory(long complexityLimit, int operatorLimit, TypeKlass ownerClass,
            Type resultType, boolean exceptionSafe, boolean noconsts) {
        this.complexityLimit = complexityLimit;
        this.operatorLimit = operatorLimit;
        this.ownerClass = ownerClass;
        this.resultType = resultType;
        this.exceptionSafe = exceptionSafe;
        this.noconsts = noconsts;
    }

    @Override
    public ArrayExtraction sproduce() throws ProductionFailedException {
        if (resultType instanceof TypeArray) {
            TypeArray arrayType = (TypeArray) resultType;
            int delta = PseudoRandom.randomNotZero(ProductionParams.dimensionsLimit.value()
                    - arrayType.dimensions);
            if (arrayType.dimensions + delta <= ProductionParams.dimensionsLimit.value()) {
                long arrayComplLimit = (long) (complexityLimit * 0.5 * PseudoRandom.random());
                int arrayOpLimit = (int) (operatorLimit * 0.5 * PseudoRandom.random());
                IRNodeBuilder builder = new IRNodeBuilder().setOwnerKlass(ownerClass)
                        .setExceptionSafe(exceptionSafe)
                        .setNoConsts(noconsts);
                IRNode arrayReturningExpression = builder
                        .setComplexityLimit(arrayComplLimit)
                        .setOperatorLimit(arrayOpLimit)
                        .setResultType(new TypeArray(arrayType.type, arrayType.dimensions + delta))
                        .getExpressionFactory().produce();
                ArrayList<IRNode> perDimensionExpression = new ArrayList<>(delta);
                long dimComplLimit = (long) ((complexityLimit - arrayComplLimit)
                        * PseudoRandom.random()) / delta;
                int dimOpLimit = (int) ((operatorLimit - arrayOpLimit - delta)
                        * PseudoRandom.random()) / delta;
                double chanceExpression = ProductionParams.chanceExpressionIndex.value() / 100.;
                for (int i = 0; i < delta; i++) {
                    if (PseudoRandom.randomBoolean(chanceExpression)) {
                        perDimensionExpression.add(builder.setResultType(TypeList.BYTE)
                                .setComplexityLimit(dimComplLimit)
                                .setOperatorLimit(dimOpLimit)
                                .getExpressionFactory()
                                .produce());
                    } else {
                        byte dimLimit = 0;
                        if (arrayReturningExpression instanceof ArrayCreation) {
                            ArrayCreation arratCreation = (ArrayCreation) arrayReturningExpression;
                            dimLimit = arratCreation.getDimensionSize(i);
                        } else if (arrayReturningExpression instanceof ArrayExtraction) {
                            ArrayExtraction arrayExtraction = (ArrayExtraction) arrayReturningExpression;
                            if (i < arrayExtraction.getDimsNumber())
                                dimLimit = arrayExtraction.getDim(i);
                        }
                        perDimensionExpression.add(new Literal((byte)PseudoRandom.randomNotNegative(dimLimit), TypeList.BYTE));
                    }
                }
                return new ArrayExtraction(arrayReturningExpression, perDimensionExpression);
            }
        }
        throw new ProductionFailedException();
    }
}
