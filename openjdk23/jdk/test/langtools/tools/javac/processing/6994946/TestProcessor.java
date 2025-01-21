/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import static javax.tools.Diagnostic.Kind.*;

import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;

public class TestProcessor extends JavacTestingAbstractProcessor {
   private int round = 0;

   public boolean process(Set<? extends TypeElement> annotations,
                  RoundEnvironment roundEnv) {
        if (++round == 1) {
            messager.printError("Deliberate Error");
            Trees trees = Trees.instance(processingEnv);
            TreePath elPath = trees.getPath(roundEnv.getRootElements().iterator().next());
            trees.printMessage(ERROR, "Deliberate Error on Trees",
                               elPath.getLeaf(), elPath.getCompilationUnit());
        }
        return false;
   }
}

