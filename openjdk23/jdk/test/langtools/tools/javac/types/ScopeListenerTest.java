/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8039262
 * @summary Ensure that using Types.membersClosure does not increase the number of listeners on the
 *          class's members Scope.
 * @modules jdk.compiler/com.sun.tools.javac.code:+open
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.util
 */

import com.sun.tools.javac.code.Scope;
import com.sun.tools.javac.code.Scope.ScopeListenerList;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Symtab;
import com.sun.tools.javac.code.Types;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;
import java.lang.reflect.Field;
import java.util.Collection;

public class ScopeListenerTest {

    public static void main(String[] args) throws Exception {
        new ScopeListenerTest().run();
    }

    void run() throws Exception {
        Context context = new Context();
        JavacFileManager.preRegister(context);
        Types types = Types.instance(context);
        Symtab syms = Symtab.instance(context);
        Names names = Names.instance(context);
        types.membersClosure(syms.stringType, true);
        types.membersClosure(syms.stringType, false);

        int listenerCount = listenerCount(syms.stringType.tsym.members());

        for (int i = 0; i < 100; i++) {
            types.membersClosure(syms.stringType, true);
            types.membersClosure(syms.stringType, false);
        }

        int newListenerCount = listenerCount(syms.stringType.tsym.members());

        if (listenerCount != newListenerCount) {
            throw new AssertionError("Orig listener count: " + listenerCount +
                                     "; new listener count: " + newListenerCount);
        }

        for (Symbol s : types.membersClosure(syms.stringType, true).getSymbols())
            ;
        for (Symbol s : types.membersClosure(syms.stringType, false).getSymbolsByName(names.fromString("substring")))
            ;
    }

    int listenerCount(Scope s) throws ReflectiveOperationException {
        Field listenersListField = Scope.class.getDeclaredField("listeners");
        listenersListField.setAccessible(true);
        Field listenersField = ScopeListenerList.class.getDeclaredField("listeners");
        listenersField.setAccessible(true);
        return ((Collection<?>)listenersField.get(listenersListField.get(s))).size();
    }

}
