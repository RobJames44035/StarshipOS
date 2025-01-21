/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package com.sun.tools.javac.comp;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Types;
import com.sun.tools.javac.jvm.PoolConstant;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCLiteral;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.tree.TreeInfo;
import com.sun.tools.javac.tree.TreeScanner;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** A tree visitor that computes a hash code. */
public class TreeHasher extends TreeScanner {

    private final Map<Symbol, Integer> symbolHashes;
    private final Types types;
    private int result = 17;

    public TreeHasher(Types types, Map<Symbol, Integer> symbolHashes) {
        this.symbolHashes = Objects.requireNonNull(symbolHashes);
        this.types = types;
    }

    public static int hash(Types types, JCTree tree, Collection<? extends Symbol> symbols) {
        if (tree == null) {
            return 0;
        }
        Map<Symbol, Integer> symbolHashes = new HashMap<>();
        symbols.forEach(s -> symbolHashes.put(s, symbolHashes.size()));
        TreeHasher hasher = new TreeHasher(types, symbolHashes);
        tree.accept(hasher);
        return hasher.result;
    }

    private void hash(Object object) {
        result = 31 * result + Objects.hashCode(object);
    }

    @Override
    public void scan(JCTree tree) {
        if (tree == null) {
            return;
        }
        tree = TreeInfo.skipParens(tree);
        if (tree.type != null) {
            Object value = tree.type.constValue();
            if (value != null) {
                hash(value);
                return;
            }
        }
        hash(tree.getTag());
        tree.accept(this);
    }

    @Override
    public void visitLiteral(JCLiteral tree) {
        hash(tree.value);
        super.visitLiteral(tree);
    }

    @Override
    public void visitClassDef(JCClassDecl tree) {
        hash(tree.sym);
    }

    @Override
    public void visitIdent(JCIdent tree) {
        Symbol sym = tree.sym;
        if (sym != null) {
            Integer hash = symbolHashes.get(sym);
            if (hash != null) {
                hash(hash);
                return;
            }
        }
        hashSymbol(sym);
    }

    @Override
    public void visitSelect(JCFieldAccess tree) {
        hashSymbol(tree.sym);
        super.visitSelect(tree);
    }

    private void hashSymbol(Symbol sym) {
        if (sym instanceof PoolConstant.Dynamic dynamic) {
            hash(dynamic.bsmKey(types));
        } else {
            hash(sym);
        }
    }

    @Override
    public void visitVarDef(JCVariableDecl tree) {
        symbolHashes.computeIfAbsent(tree.sym, k -> symbolHashes.size());
        super.visitVarDef(tree);
    }
}
