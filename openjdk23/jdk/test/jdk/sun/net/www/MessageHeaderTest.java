/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/**
 * @test
 * @bug 8003948 8133686
 * @modules java.base/sun.net.www
 * @run testng MessageHeaderTest
 */

import java.io.*;
import java.util.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import sun.net.www.MessageHeader;

public class MessageHeaderTest {
    /* This test checks to see if the MessageHeader.getHeaders method
       returns headers with the same value field in the order they were added
       to conform with RFC2616
   */

    @Test
    public void reverseMessageHeadersTest() throws Exception {
        String errorMessageTemplate = "Expected Headers = %s, Actual Headers = %s";
        var expectedHeaders = Arrays.asList("a", "b", "c");

        MessageHeader testHeader = new MessageHeader();
        testHeader.add("test", "a");
        testHeader.add("test", "b");
        testHeader.add("test", "c");

        var actualHeaders = testHeader.getHeaders().get("test");

        Assert.assertEquals(expectedHeaders, actualHeaders, String.format(errorMessageTemplate, expectedHeaders.toString(), actualHeaders.toString()));
    }

    @Test
    public void ntlmNegotiateTest () throws Exception {
        String expected[] = {
            "{null: HTTP/1.1 200 Ok}{Foo: bar}{Bar: foo}{WWW-Authenticate: NTLM sdsds}",
            "{null: HTTP/1.1 200 Ok}{Foo: bar}{Bar: foo}{WWW-Authenticate: }",
            "{null: HTTP/1.1 200 Ok}{Foo: bar}{Bar: foo}{WWW-Authenticate: NTLM sdsds}",
            "{null: HTTP/1.1 200 Ok}{Foo: bar}{Bar: foo}{WWW-Authenticate: NTLM sdsds}",
            "{null: HTTP/1.1 200 Ok}{Foo: bar}{Bar: foo}{WWW-Authenticate: NTLM sdsds}{Bar: foo}",
            "{null: HTTP/1.1 200 Ok}{WWW-Authenticate: Negotiate}{Foo: bar}{Bar: foo}{WWW-Authenticate: NTLM}{Bar: foo}{WWW-Authenticate: Kerberos}",
            "{null: HTTP/1.1 200 Ok}{Foo: foo}{Bar: }{WWW-Authenticate: NTLM blob}{Bar: foo blob}"
        };

        boolean[] expectedResult = {
            false, false, true, true, true, false, false
        };

        String[] headers = {
            "HTTP/1.1 200 Ok\r\nFoo: bar\r\nBar: foo\r\nWWW-Authenticate: NTLM sdsds",
            "HTTP/1.1 200 Ok\r\nFoo: bar\r\nBar: foo\r\nWWW-Authenticate:",
            "HTTP/1.1 200 Ok\r\nFoo: bar\r\nBar: foo\r\nWWW-Authenticate: NTLM sdsds\r\nWWW-Authenticate: Negotiate",
            "HTTP/1.1 200 Ok\r\nFoo: bar\r\nBar: foo\r\nWWW-Authenticate: NTLM sdsds\r\nWWW-Authenticate: Negotiate\r\nWWW-Authenticate: Kerberos",
            "HTTP/1.1 200 Ok\r\nWWW-Authenticate: Negotiate\r\nFoo: bar\r\nBar: foo\r\nWWW-Authenticate: NTLM sdsds\r\nBar: foo\r\nWWW-Authenticate: Kerberos",
            "HTTP/1.1 200 Ok\r\nWWW-Authenticate: Negotiate\r\nFoo: bar\r\nBar: foo\r\nWWW-Authenticate: NTLM\r\nBar: foo\r\nWWW-Authenticate: Kerberos",
            "HTTP/1.1 200 Ok\r\nFoo: foo\r\nBar:\r\nWWW-Authenticate: NTLM blob\r\nBar: foo blob"
        };

        for (int i=0; i<7; i++) {
            ByteArrayInputStream bis = new ByteArrayInputStream(headers[i].getBytes());
            MessageHeader h = new MessageHeader(bis);
            String before = h.toString();
            before = before.substring(before.indexOf('{'));
            boolean result = h.filterNTLMResponses("WWW-Authenticate");
            String after = h.toString();
            after = after.substring(after.indexOf('{'));
            Assert.assertEquals(expected[i], after, i + " expected != after");
            Assert.assertEquals(result, expectedResult[i], i + " result != expectedResult");
        }
    }
}
