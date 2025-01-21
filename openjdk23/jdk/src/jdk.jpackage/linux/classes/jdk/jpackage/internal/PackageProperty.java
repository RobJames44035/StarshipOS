/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jpackage.internal;

import java.text.MessageFormat;

final class PackageProperty {
    /**
     * Constructor
     *
     * @param name property name
     * @param expectedValue expected property value
     * @param substString substitution string to be placed in resource file to
     * be replaced with the expected property value by jpackage at package build
     * time
     * @param customResource name of custom resource from resource directory in
     * which this package property can be set
     */
    PackageProperty(String name, String expectedValue, String substString,
            String customResource) {
        this.name = name;
        this.expectedValue = expectedValue;
        this.substString = substString;
        this.customResource = customResource;
    }

    ConfigException verifyValue(String actualValue) {
        if (expectedValue.equals(actualValue)) {
            return null;
        }

        final String advice;
        if (substString != null) {
            advice = MessageFormat.format(I18N.getString(
                    "error.unexpected-package-property.advice"), substString,
                    actualValue, name, customResource);
        } else {
            advice = MessageFormat.format(I18N.getString(
                    "error.unexpected-default-package-property.advice"), name,
                    customResource);
        }

        return new ConfigException(MessageFormat.format(I18N.getString(
                "error.unexpected-package-property"), name,
                expectedValue, actualValue, customResource, substString), advice);
    }

    final String name;
    private final String expectedValue;
    private final String substString;
    private final String customResource;
}
