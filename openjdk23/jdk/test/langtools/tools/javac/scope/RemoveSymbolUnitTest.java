/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8080842
 * @summary Ensure Scope impl can cope with remove() when a field and method share the name.
 * @modules jdk.compiler/com.sun.tools.javac.code
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.util
 */

import com.sun.tools.javac.util.*;
import com.sun.tools.javac.code.*;
import com.sun.tools.javac.code.Scope.*;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Symbol.*;
import com.sun.tools.javac.file.JavacFileManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RemoveSymbolUnitTest {

    Context context;
    Names names;
    Symtab symtab;

    public static void main(String... args) throws Exception {
        new RemoveSymbolUnitTest().run();
    }

    public void run() {
        context = new Context();
        JavacFileManager.preRegister(context); // required by ClassReader which is required by Symtab
        names = Names.instance(context);
        symtab = Symtab.instance(context);

        Name hasNext =  names.fromString("hasNext");
        ClassSymbol clazz = new ClassSymbol(0,
                                            names.fromString("X"),
                                            Type.noType,
                                            symtab.unnamedModule.unnamedPackage);

        VarSymbol v = new VarSymbol(0, hasNext, Type.noType, clazz);
        MethodSymbol m = new MethodSymbol(0, hasNext, Type.noType, clazz);

        // Try enter and remove in different shuffled combinations.
        // working with fresh scope each time.
        WriteableScope cs = writeableScope(clazz, v, m);
        cs.remove(v);
        Symbol s = cs.findFirst(hasNext);
        if (s != m)
            throw new AssertionError("Wrong symbol");

        cs = writeableScope(clazz, m, v);
        cs.remove(v);
        s = cs.findFirst(hasNext);
        if (s != m)
            throw new AssertionError("Wrong symbol");

        cs = writeableScope(clazz, v, m);
        cs.remove(m);
        s = cs.findFirst(hasNext);
        if (s != v)
            throw new AssertionError("Wrong symbol");

        cs = writeableScope(clazz);
        cs.enter(m);
        cs.enter(v);
        cs.remove(m);
        s = cs.findFirst(hasNext);
        if (s != v)
            throw new AssertionError("Wrong symbol");

        // Test multiple removals in the same scope.
        VarSymbol v1 = new VarSymbol(0, names.fromString("name1"), Type.noType, clazz);
        VarSymbol v2 = new VarSymbol(0, names.fromString("name2"), Type.noType, clazz);
        VarSymbol v3 = new VarSymbol(0, names.fromString("name3"), Type.noType, clazz);
        VarSymbol v4 = new VarSymbol(0, names.fromString("name4"), Type.noType, clazz);

        cs = writeableScope(clazz, v1, v2, v3, v4);
        cs.remove(v2);
        assertRemainingSymbols(cs, v1, v3, v4);
        cs.remove(v3);
        assertRemainingSymbols(cs, v1, v4);
        cs.remove(v1);
        assertRemainingSymbols(cs, v4);
        cs.remove(v4);
        assertRemainingSymbols(cs);
    }

    private WriteableScope writeableScope(ClassSymbol classSymbol, Symbol... symbols) {
        WriteableScope cs = WriteableScope.create(classSymbol);
        for (Symbol symbol : symbols) {
            cs.enter(symbol);
        }
        return cs;
    }

    private void assertRemainingSymbols(WriteableScope cs, Symbol... symbols) {
      List<Symbol> expectedSymbols = Arrays.asList(symbols);
      List<Symbol> actualSymbols = new ArrayList<>();
      cs.getSymbols().forEach(symbol -> actualSymbols.add(symbol));
      // The symbols are stored in reverse order
      Collections.reverse(actualSymbols);
      if (!actualSymbols.equals(expectedSymbols)) {
          throw new AssertionError(
              String.format("Wrong symbols: %s. Expected %s", actualSymbols, expectedSymbols));
      }
    }
}
