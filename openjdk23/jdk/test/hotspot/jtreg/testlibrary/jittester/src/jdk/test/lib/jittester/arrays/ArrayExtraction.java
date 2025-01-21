/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package jdk.test.lib.jittester.arrays;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.Literal;
import jdk.test.lib.jittester.LocalVariable;
import jdk.test.lib.jittester.types.TypeArray;
import jdk.test.lib.jittester.visitors.Visitor;


/*
Array extraction produces and array with N dimentions from an array with M
dimentions, where N < M.
 */
public class ArrayExtraction extends IRNode {
    private final List<Byte> dims;
    public ArrayExtraction(IRNode array, ArrayList<IRNode> dimensionExpressions) {
        super(array.getResultType());
        addChild(array);
        addChildren(dimensionExpressions);
        if (array instanceof ArrayCreation) {
            dims = new ArrayList<>();
            ArrayCreation ac = (ArrayCreation) array;
            for (int i = dimensionExpressions.size(); i < ac.getDimensionsCount(); ++i) {
                dims.add(ac.getDimensionSize(i));
            }
        } else if (array instanceof ArrayExtraction) {
            dims = new ArrayList<>();
            ArrayExtraction ae = (ArrayExtraction) array;
            for (int i = dimensionExpressions.size(); i < ae.getDimsNumber(); ++i) {
                dims.add(ae.getDim(i));
            }
        } else if (array instanceof LocalVariable) {
            LocalVariable loc = (LocalVariable) array;
            TypeArray type = (TypeArray) loc.getVariableInfo().type;
            dims = type.getDims();
            for (int i = dimensionExpressions.size(); i < type.dimensions; ++i) {
                dims.add(type.getDims().get(i));
            }
        } else {
            dims = dimensionExpressions.stream()
                .map(d -> {
                    if (d instanceof Literal) {
                        Literal n = (Literal) d;
                        return (Byte)n.getValue();
                    }
                    return (byte)0;
                })
                .collect(Collectors.toList());
        }
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public byte getDim(int dim) {
        return dims.get(dim);
    }

    public int getDimsNumber() {
        return dims.size();
    }
}
