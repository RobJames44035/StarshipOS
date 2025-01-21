/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple;

import java.util.Map;

/**
 * <p>Represents objects charged with taking a set of option descriptions and producing some help text from them.</p>
 *
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
public interface HelpFormatter {
    /**
     * Produces help text, given a set of option descriptors.
     *
     * @param options descriptors for the configured options of a parser
     * @return text to be used as help
     * @see OptionParser#printHelpOn(java.io.Writer)
     * @see OptionParser#formatHelpWith(HelpFormatter)
     */
    String format( Map<String, ? extends OptionDescriptor> options );
}
