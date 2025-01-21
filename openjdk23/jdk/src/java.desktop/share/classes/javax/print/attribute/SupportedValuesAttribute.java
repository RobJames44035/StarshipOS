/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package javax.print.attribute;

/**
 * Interface {@code SupportedValuesAttribute} is a tagging interface which a
 * printing attribute class implements to indicate the attribute describes the
 * supported values for another attribute. For example, if a Print Service
 * instance supports the {@link javax.print.attribute.standard.Copies Copies}
 * attribute, the Print Service instance will have a
 * {@link javax.print.attribute.standard.CopiesSupported CopiesSupported}
 * attribute, which is a {@code SupportedValuesAttribute} giving the legal
 * values a client may specify for the
 * {@link javax.print.attribute.standard.Copies Copies} attribute.
 *
 * @author Alan Kaminsky
 */
public interface SupportedValuesAttribute extends Attribute {
}
