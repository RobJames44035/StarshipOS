/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester;

import java.util.ArrayList;
import java.util.List;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.visitors.Visitor;

public class PrintVariables extends IRNode {
    private final ArrayList<Symbol> vars;

    public PrintVariables(TypeKlass owner, int level) {
        super(TypeList.VOID);
        this.owner = owner;
        this.vars = SymbolTable.getAllCombined(owner, VariableInfo.class);
        this.level = level;
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public List<Symbol> getVars() {
        return vars;
    }
}
