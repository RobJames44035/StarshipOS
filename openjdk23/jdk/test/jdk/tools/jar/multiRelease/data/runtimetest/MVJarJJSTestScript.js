/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

var Main = Java.type("testpackage.Main");
var mainVersion = Main.getMainVersion();
var helperVersion = Main.getHelperVersion();
var resourceVersion = Main.getResourceVersion();
print("Main version: " + mainVersion);
print("Helpers version: " + helperVersion);
print("Resource version: " + resourceVersion);
