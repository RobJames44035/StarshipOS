/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import java.util.ArrayList;
import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.NonStaticMemberVariable;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.Symbol;
import jdk.test.lib.jittester.SymbolTable;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.VariableInfo;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.utils.PseudoRandom;

class NonStaticMemberVariableFactory extends Factory<NonStaticMemberVariable> {
    private final Type type;
    private final int flags;
    private final long complexityLimit;
    private final int operatorLimit;
    private final boolean exceptionSafe;
    private final Type ownerClass;

    NonStaticMemberVariableFactory(long complexityLimit, int operatorLimit,
            TypeKlass ownerClass, Type type, int flags, boolean exceptionSafe) {
        this.ownerClass = ownerClass;
        this.type = type;
        this.flags = flags;
        this.complexityLimit = complexityLimit;
        this.operatorLimit = operatorLimit;
        this.exceptionSafe = exceptionSafe;
    }

    @Override
    public NonStaticMemberVariable produce() throws ProductionFailedException {
        // Get the variables of the requested type from SymbolTable
        ArrayList<Symbol> variables = new ArrayList<>(SymbolTable.get(type, VariableInfo.class));
        if (!variables.isEmpty()) {
            PseudoRandom.shuffle(variables);
            IRNodeBuilder builder = new IRNodeBuilder().setComplexityLimit(complexityLimit)
                    .setOperatorLimit(operatorLimit)
                    .setOwnerKlass((TypeKlass) ownerClass)
                    .setExceptionSafe(exceptionSafe)
                    .setNoConsts(false);
            for (Symbol symbol : variables) {
                VariableInfo varInfo = (VariableInfo) symbol;
                if ((varInfo.flags & VariableInfo.FINAL) == (flags & VariableInfo.FINAL)
                        && (varInfo.flags & VariableInfo.INITIALIZED) == (flags & VariableInfo.INITIALIZED)
                        && (varInfo.flags & VariableInfo.STATIC) == 0
                        && (varInfo.flags & VariableInfo.LOCAL) == 0) {
                    try {
                        IRNode object = builder.setResultType(varInfo.owner)
                                .getExpressionFactory().produce();
                        return new NonStaticMemberVariable(object, varInfo);
                    } catch (ProductionFailedException e) {
                    }
                }
            }
        }
        throw new ProductionFailedException();
    }
}
