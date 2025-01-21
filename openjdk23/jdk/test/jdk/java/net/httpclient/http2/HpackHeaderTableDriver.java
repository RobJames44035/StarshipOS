/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8153353
 * @modules java.net.http/jdk.internal.net.http.hpack
 *          jdk.localedata
 * @key randomness
 * @compile/module=java.net.http jdk/internal/net/http/hpack/SpecHelper.java
 * @compile/module=java.net.http jdk/internal/net/http/hpack/TestHelper.java
 * @compile/module=java.net.http jdk/internal/net/http/hpack/BuffersTestingKit.java
 * @run testng/othervm java.net.http/jdk.internal.net.http.hpack.HeaderTableTest
 */
public class HpackHeaderTableDriver { }
