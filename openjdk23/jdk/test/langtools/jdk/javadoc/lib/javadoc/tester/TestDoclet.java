/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package javadoc.tester;

import java.util.Locale;
import java.util.Set;
import javax.lang.model.SourceVersion;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

/**
 * A minimal base class for test doclets.
 *
 * The {@link Doclet#run(DocletEnvironment) run} method must still be provided by subtypes.
 */
public abstract class TestDoclet implements Doclet {
    protected Locale locale;
    protected Reporter reporter;

    @Override
    public void init(Locale locale, Reporter reporter) {
        this.locale = locale;
        this.reporter = reporter;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public Set<? extends Option> getSupportedOptions() {
        return Set.of();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
