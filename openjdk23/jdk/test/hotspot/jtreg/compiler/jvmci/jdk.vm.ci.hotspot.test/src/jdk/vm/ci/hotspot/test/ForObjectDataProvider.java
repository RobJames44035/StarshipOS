/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.vm.ci.hotspot.test;

import org.testng.annotations.DataProvider;

public class ForObjectDataProvider {
    @DataProvider(name = "forObjectDataProvider")
    public static Object[][] forObjectDataProvider() {
        return new Object[][]{
                        {TestHelper.DUMMY_CLASS_INSTANCE.objectField,
                                        "Object[Object@" + TestHelper.DUMMY_CLASS_INSTANCE.objectField.hashCode() + "]"},
                        {TestHelper.DUMMY_CLASS_INSTANCE.stringField,
                                        "Object[String:\"" + TestHelper.DUMMY_CLASS_INSTANCE.stringField + "\"]"},
                        {TestHelper.DUMMY_CLASS_INSTANCE.booleanField,
                                        "Object[" + TestHelper.DUMMY_CLASS_INSTANCE.booleanField + "]"},
                        {TestHelper.DUMMY_CLASS_INSTANCE.byteField,
                                        "Object[" + TestHelper.DUMMY_CLASS_INSTANCE.byteField + "]"},
                        {TestHelper.DUMMY_CLASS_INSTANCE.charField,
                                        "Object[" + TestHelper.DUMMY_CLASS_INSTANCE.charField + "]"},
                        {TestHelper.DUMMY_CLASS_INSTANCE.shortField,
                                        "Object[" + TestHelper.DUMMY_CLASS_INSTANCE.shortField + "]"},
                        {TestHelper.DUMMY_CLASS_INSTANCE.intField,
                                        "Object[" + TestHelper.DUMMY_CLASS_INSTANCE.intField + "]"},
                        {TestHelper.DUMMY_CLASS_INSTANCE.longField,
                                        "Object[" + TestHelper.DUMMY_CLASS_INSTANCE.longField + "]"},
                        {TestHelper.DUMMY_CLASS_INSTANCE.floatField,
                                        "Object[" + TestHelper.DUMMY_CLASS_INSTANCE.floatField + "]"},
                        {TestHelper.DUMMY_CLASS_INSTANCE.doubleField,
                                        "Object[" + TestHelper.DUMMY_CLASS_INSTANCE.doubleField + "]"},
                        {new Object[0], "Object[Object[" + 0 + "]{}]"}, {new Object[1], "Object[Object[" + 1 + "]{null}]"},
                        {null, "Object[null]"}};
    }
}
