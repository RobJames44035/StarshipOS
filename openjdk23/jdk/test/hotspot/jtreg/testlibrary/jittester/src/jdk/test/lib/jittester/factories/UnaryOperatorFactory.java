/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.OperatorKind;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.SymbolTable;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.UnaryOperator;

public abstract class UnaryOperatorFactory extends OperatorFactory<UnaryOperator> {
    protected final OperatorKind opKind;
    protected final Type resultType;
    protected final Type ownerClass;

    protected UnaryOperatorFactory(OperatorKind opKind, long complexityLimit, int operatorLimit,
            Type ownerClass, Type resultType, boolean exceptionSafe, boolean noconsts) {
        super(opKind.priority, complexityLimit, operatorLimit, exceptionSafe, noconsts);
        this.opKind = opKind;
        this.resultType = resultType;
        this.ownerClass = ownerClass;
    }

    protected Type generateType() {
        return resultType;
    }

    protected abstract UnaryOperator generateProduction(Type type) throws ProductionFailedException;

    protected abstract boolean isApplicable(Type resultType);

    @Override
    public UnaryOperator produce() throws ProductionFailedException {
        if (!isApplicable(resultType)) {
            //avoid implicit use of resultType.toString()
            throw new ProductionFailedException("Type " + resultType.getName()
                    + " is not applicable by " + getClass().getName());
        }
        Type type;
        try {
            type = generateType();
        } catch (Exception ex) {
            throw new ProductionFailedException(ex.getMessage());
        }
        try {
            SymbolTable.push();
            UnaryOperator result = generateProduction(type);
            SymbolTable.merge();
            return result;
        } catch (ProductionFailedException e) {
            SymbolTable.pop();
            throw e;
        }
    }
}
