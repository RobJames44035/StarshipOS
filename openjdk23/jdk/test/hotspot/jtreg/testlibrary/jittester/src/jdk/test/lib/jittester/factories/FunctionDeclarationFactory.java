/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.Symbol;
import jdk.test.lib.jittester.SymbolTable;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.VariableInfo;
import jdk.test.lib.jittester.functions.ArgumentDeclaration;
import jdk.test.lib.jittester.functions.FunctionDeclaration;
import jdk.test.lib.jittester.functions.FunctionDefinition;
import jdk.test.lib.jittester.functions.FunctionInfo;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.utils.PseudoRandom;

class FunctionDeclarationFactory extends Factory<FunctionDeclaration> {
    private final Type resultType;
    private final TypeKlass ownerClass;
    private final String name;
    private final int memberFunctionsArgLimit;
    private final int flags;

    FunctionDeclarationFactory(String name, TypeKlass ownerClass, Type resultType,
            int memberFunctionsArgLimit, int flags) {
        this.name = name;
        this.ownerClass = ownerClass;
        this.resultType = resultType;
        this.memberFunctionsArgLimit = memberFunctionsArgLimit;
        this.flags = flags;
    }

    @Override
    public FunctionDeclaration produce() throws ProductionFailedException {
        Type resType = resultType;
        if (resType == null) {
            List<Type> types = new ArrayList<>(TypeList.getAll());
            types.add(TypeList.VOID);
            resType = PseudoRandom.randomElement(types);
        }
        int argNumber = (int) (PseudoRandom.random() * memberFunctionsArgLimit);
        ArrayList<VariableInfo> argumentsInfo = new ArrayList<>(argNumber + 1);
        argumentsInfo.add(new VariableInfo("this", ownerClass, ownerClass,
                VariableInfo.FINAL | VariableInfo.LOCAL | VariableInfo.INITIALIZED));
        ArrayList<ArgumentDeclaration> argumentsDeclaration = new ArrayList<>(argNumber);
        SymbolTable.push();
        FunctionInfo functionInfo;
        IRNodeBuilder builder = new IRNodeBuilder().setArgumentType(ownerClass);
        try {
            int i = 0;
            for (; i < argNumber; i++) {
                ArgumentDeclaration d = builder.setVariableNumber(i)
                        .getArgumentDeclarationFactory().produce();
                argumentsDeclaration.add(d);
                argumentsInfo.add(d.variableInfo);
            }
            Collection<Symbol> thisKlassFuncs = SymbolTable
                    .getAllCombined(ownerClass, FunctionInfo.class);
            Collection<Symbol> parentFuncs = FunctionDefinition.getFuncsFromParents(ownerClass);
            while (true) {
                functionInfo = new FunctionInfo(name, ownerClass, resType, 0, flags, argumentsInfo);
                if (thisKlassFuncs.contains(functionInfo)
                        || FunctionDefinition.isInvalidOverride(functionInfo, parentFuncs)) {
                    // try changing the signature, and go checking again.
                    ArgumentDeclaration d = builder.setVariableNumber(i++)
                            .getArgumentDeclarationFactory().produce();
                    argumentsDeclaration.add(d);
                    argumentsInfo.add(d.variableInfo);
                } else {
                    break;
                }
            }
        } finally {
            SymbolTable.pop();
        }
        //addChildren(argumentsDeclaration); // not neccessary while complexity is 0
        functionInfo = new FunctionInfo(name, ownerClass, resType, 0, flags, argumentsInfo);
        // If it's all ok, add the function to the symbol table.
        SymbolTable.add(functionInfo);
        return new FunctionDeclaration(functionInfo, argumentsDeclaration);
    }
}
