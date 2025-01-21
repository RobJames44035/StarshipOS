/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.jmx.info;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.jfr.jmx.JmxHelper;

import jdk.jfr.Configuration;
import jdk.management.jfr.ConfigurationInfo;
import jdk.test.lib.Asserts;

/**
 * @test
 * @key jfr
 * @summary Test for ConfigurationInfo. Compare infos from java API and jmx API.
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jmx.info.TestConfigurationInfo
 */
public class TestConfigurationInfo {
    public static void main(String[] args) throws Throwable {
        List<ConfigurationInfo> configInfos = JmxHelper.getFlighteRecorderMXBean().getConfigurations();
        Asserts.assertFalse(configInfos.isEmpty(), "No ConfigurationInfos found");

        Map<String, Configuration> configs = new HashMap<>();
        for (Configuration config : Configuration.getConfigurations()) {
            configs.put(config.getName(), config);
        }
        Asserts.assertFalse(configs.isEmpty(), "No Configurations found");

        for (ConfigurationInfo configInfo : configInfos) {
            final String key = configInfo.getName();
            Configuration config = configs.remove(key);
            Asserts.assertNotNull(config, "No Configuration for name " + key);

            System.out.println("getDescription:" + configInfo.getDescription());
            System.out.println("getLabel:" + configInfo.getLabel());
            System.out.println("getName:" + configInfo.getName());
            System.out.println("getProvider:" + configInfo.getProvider());

            Asserts.assertEquals(configInfo.getContents(), config.getContents(), "Wrong contents");
            Asserts.assertEquals(configInfo.getDescription(), config.getDescription(), "Wrong description");
            Asserts.assertEquals(configInfo.getLabel(), config.getLabel(), "Wrong label");
            Asserts.assertEquals(configInfo.getName(), config.getName(), "Wrong name");
            Asserts.assertEquals(configInfo.getProvider(), config.getProvider(), "Wrong provider");

            verifySettingsEqual(config, configInfo);
        }

        // Verify that all EventTypes have been matched.
        if (!configs.isEmpty()) {
            for (String name : configs.keySet()) {
                System.out.println("Found extra Configuration with name " + name);
            }
            Asserts.fail("Found extra Configuration");
        }
    }

    private static void verifySettingsEqual(Configuration config, ConfigurationInfo configInfo) {
        Map<String, String> javaSettings = config.getSettings();
        Map<String, String> jmxSettings = configInfo.getSettings();

        Asserts.assertFalse(javaSettings.isEmpty(), "No Settings found in java apa");
        Asserts.assertFalse(jmxSettings.isEmpty(), "No Settings found in jmx api");

        for (String name : jmxSettings.keySet().toArray(new String[0])) {
            System.out.printf("%s: jmx=%s, java=%s%n", name, jmxSettings.get(name), javaSettings.get(name));
            Asserts.assertNotNull(javaSettings.get(name), "No java setting for " + name);
            Asserts.assertEquals(jmxSettings.get(name), javaSettings.get(name), "Wrong value for setting");
            javaSettings.remove(name);
        }

        // Verify that all Settings have been matched.
        if (!javaSettings.isEmpty()) {
            for (String name : javaSettings.keySet()) {
                System.out.println("Found extra Settings name " + name);
            }
            Asserts.fail("Found extra Setting in java api");
        }
    }

}
