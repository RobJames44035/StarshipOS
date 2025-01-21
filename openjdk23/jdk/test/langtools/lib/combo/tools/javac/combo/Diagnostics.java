/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package tools.javac.combo;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
* A container for compiler diagnostics, separated into errors and warnings,
 * used by JavacTemplateTestBase.
 *
 * @author Brian Goetz
*/
public class Diagnostics implements javax.tools.DiagnosticListener<JavaFileObject> {

    protected List<Diagnostic<? extends JavaFileObject>> diags = new ArrayList<>();

    public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
        diags.add(diagnostic);
    }

    /** Were there any errors found? */
    public boolean errorsFound() {
        return diags.stream()
                    .anyMatch(d -> d.getKind() == Diagnostic.Kind.ERROR);
    }

    /** Get all diagnostic keys */
    public List<String> keys() {
        return diags.stream()
                    .map(Diagnostic::getCode)
                    .collect(toList());
    }

    public Diagnostic<?> getDiagWithKey(String key) {
        for (Diagnostic<?> d : diags) {
            if (d.getCode().equals(key)) {
                return d;
            }
        }
        return null;
    }

    public List<Diagnostic<?>> getAllDiags() {
        return diags.stream().map(d -> (Diagnostic<?>)d).collect(toList());
    }

    /** Do the diagnostics contain the specified error key? */
    public boolean containsErrorKey(String key) {
        return diags.stream()
                    .filter(d -> d.getKind() == Diagnostic.Kind.ERROR)
                    .anyMatch(d -> d.getCode().equals(key));
    }

    /** Do the diagnostics contain the specified warning key? */
    public boolean containsWarningKey(String key) {
        return diags.stream()
                    .filter(d -> d.getKind() == Diagnostic.Kind.WARNING || d.getKind() == Diagnostic.Kind.MANDATORY_WARNING)
                    .anyMatch(d -> d.getCode().equals(key));
    }

    /** Get the error keys */
    public List<String> errorKeys() {
        return diags.stream()
                    .filter(d -> d.getKind() == Diagnostic.Kind.ERROR)
                    .map(Diagnostic::getCode)
                    .collect(toList());
    }

    public String toString() { return keys().toString(); }

    /** Clear all diagnostic state */
    public void reset() {
        diags.clear();
    }
}
