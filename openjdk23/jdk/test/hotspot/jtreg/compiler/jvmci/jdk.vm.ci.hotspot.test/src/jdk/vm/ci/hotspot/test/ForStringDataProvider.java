/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.vm.ci.hotspot.test;

import org.testng.annotations.DataProvider;

import static jdk.vm.ci.hotspot.test.TestHelper.DUMMY_CLASS_INSTANCE;

public class ForStringDataProvider {
    @DataProvider(name = "forStringDataProvider")
    public static Object[][] forStringDataProvider() {
        return new Object[][]{
                        {DUMMY_CLASS_INSTANCE.stringField, "Object[String:\"" + DUMMY_CLASS_INSTANCE.stringField + "\"]"},
                        {DUMMY_CLASS_INSTANCE.stringEmptyField, "Object[String:\"\"]"},
                        {null, "Object[null]"}};
    }
}
