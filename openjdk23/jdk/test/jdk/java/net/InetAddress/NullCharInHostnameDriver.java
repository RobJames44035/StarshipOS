/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8300038
 * @modules java.base/java.net
 * @compile/module=java.base java/net/NullCharInHostname.java
 * @summary Make new version of JNU_GetStringPlatformChars which checks for null characters
 * @run main/othervm java.base/java.net.NullCharInHostname
 * @run main/othervm -Dfile.encoding=COMPAT java.base/java.net.NullCharInHostname
 */

public class NullCharInHostnameDriver { }
