/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package crules;

import com.sun.source.tree.LambdaExpressionTree.BodyKind;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TaskEvent.Kind;
import com.sun.tools.javac.code.Kinds;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCLambda;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.tree.JCTree.Tag;
import com.sun.tools.javac.tree.TreeInfo;
import com.sun.tools.javac.tree.TreeScanner;
import com.sun.tools.javac.util.Assert;

/**This analyzer guards against complex messages (i.e. those that use string concatenation) passed
 * to various Assert.check methods.
 */
public class AssertCheckAnalyzer extends AbstractCodingRulesAnalyzer {

    enum AssertOverloadKind {
        EAGER("crules.should.not.use.eager.string.evaluation"),
        LAZY("crules.should.not.use.lazy.string.evaluation"),
        NONE(null);

        String errKey;

        AssertOverloadKind(String errKey) {
            this.errKey = errKey;
        }

        boolean simpleArgExpected() {
            return this == AssertOverloadKind.EAGER;
        }
    }

    public AssertCheckAnalyzer(JavacTask task) {
        super(task);
        treeVisitor = new AssertCheckVisitor();
        eventKind = Kind.ANALYZE;
    }

    class AssertCheckVisitor extends TreeScanner {

        @Override
        public void visitApply(JCMethodInvocation tree) {
            Symbol m = TreeInfo.symbolFor(tree);
            AssertOverloadKind ak = assertOverloadKind(m);
            if (ak != AssertOverloadKind.NONE &&
                !m.name.contentEquals("error")) {
                JCExpression lastParam = tree.args.last();
                if (isSimpleStringArg(lastParam) != ak.simpleArgExpected()) {
                    messages.error(lastParam, ak.errKey);
                }
            }

            super.visitApply(tree);
        }

        AssertOverloadKind assertOverloadKind(Symbol method) {
            if (method == null ||
                !method.owner.getQualifiedName().contentEquals(Assert.class.getName()) ||
                method.type.getParameterTypes().tail == null) {
                return AssertOverloadKind.NONE;
            }
            Type formal = method.type.getParameterTypes().last();
            if (types.isSameType(formal, syms.stringType)) {
                return AssertOverloadKind.EAGER;
            } else if (types.isSameType(types.erasure(formal), types.erasure(syms.supplierType))) {
                return AssertOverloadKind.LAZY;
            } else {
                return AssertOverloadKind.NONE;
            }
        }

        boolean isSimpleStringArg(JCExpression e) {
            switch (e.getTag()) {
                case LAMBDA:
                    JCLambda lambda = (JCLambda)e;
                    return (lambda.getBodyKind() == BodyKind.EXPRESSION) &&
                            isSimpleStringArg((JCExpression)lambda.body);
                default:
                    Symbol argSym = TreeInfo.symbolFor(e);
                    return (e.type.constValue() != null ||
                            (argSym != null && argSym.kind == Kinds.Kind.VAR));
            }
        }
    }
}
