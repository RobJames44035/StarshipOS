/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8087112
 * @summary Basic test for headers
 */

import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.URI;
import java.util.List;
import java.util.Iterator;

public class HeadersTest2 {
    static URI uri = URI.create("http://www.foo.com/");

    static class CompareTest {
        boolean succeed;
        List<String> nameValues1;
        List<String> nameValues2;


        /**
         * Each list contains header-name, header-value, header-name, header-value
         * sequences. The test creates two requests with the two lists
         * and compares the HttpHeaders objects returned from the requests
         */
        CompareTest(boolean succeed, List<String> l1, List<String> l2) {
            this.succeed = succeed;
            this.nameValues1 = l1;
            this.nameValues2 = l2;
        }

        public void run() {
            HttpRequest r1 = getRequest(nameValues1);
            HttpRequest r2 = getRequest(nameValues2);
            HttpHeaders h1 = r1.headers();
            HttpHeaders h2 = r2.headers();
            boolean equal = h1.equals(h2);
            if (equal && !succeed) {
                System.err.println("Expected failure");
                print(nameValues1);
                print(nameValues2);
                throw new RuntimeException();
            } else if (!equal && succeed) {
                System.err.println("Expected success");
                print(nameValues1);
                print(nameValues2);
                throw new RuntimeException();
            }

            // Ensures that headers never equal a non-HttpHeaders type
            if (h1.equals(new Object()))
                throw new RuntimeException("Unexpected h1 equals Object");

            if (h2.equals(r1))
                throw new RuntimeException("Unexpected h2 equals r1");
        }

        static void print(List<String> list) {
            System.err.print("{");
            for (String s : list) {
                System.err.print(s + " ");
            }
            System.err.println("}");
        }

        HttpRequest getRequest(List<String> headers) {
            HttpRequest.Builder builder = HttpRequest.newBuilder(uri);
            Iterator<String> iterator = headers.iterator();
            while (iterator.hasNext()) {
                String name = iterator.next();
                String value = iterator.next();
                builder.header(name, value);
            }
            return builder.GET().build();
        }
    }

    static CompareTest test(boolean s, List<String> l1, List<String> l2) {
        return new CompareTest(s, l1, l2);
    }

    static CompareTest[] compareTests = new CompareTest[] {
        test(true, List.of("Dontent-length", "99"), List.of("dontent-length", "99")),
        test(false, List.of("Dontent-length", "99"), List.of("dontent-length", "100")),
        test(false, List.of("Name1", "val1", "Name1", "val2", "name1", "val3"),
                    List.of("Name1", "val1", "Name1", "val2")),
        test(true, List.of("Name1", "val1", "Name1", "val2", "name1", "val3"),
                   List.of("NaMe1", "val1", "NAme1", "val2", "name1", "val3"))
    };

    public static void main(String[] args) {
        for (CompareTest test : compareTests) {
            test.run();
        }
    }
}
