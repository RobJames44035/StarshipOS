/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8016916
 * @summary UnstructuredName should support DirectoryString
 * @modules java.base/sun.security.pkcs10
 */

import java.util.Base64;
import sun.security.pkcs10.PKCS10;

public class UnstructuredName {

    // Certificate request with an Unstructured Name attribute
    static String csrStr =
        "MIIBtjCCAR8CAQAwEzERMA8GA1UEAxMIdGVzdE5hbWUwgZ8wDQYJKoZIhvcNAQEB\n" +
        "BQADgY0AMIGJAoGBAMTEIVCsM8IIhvsbzn6AwQFX5C8RGAWIrL6P5XEr1z+bvHx3\n" +
        "XhPD4tWLCR6CTKq0lTlo+QKKct7MUY7pdKShajpyYD+1YLgEve0nNd4r5kVUeoHe\n" +
        "CyIZoImONgAlmVD7M8IJjz2Vg84WVVjkHK67H5qt7Agi1hUnFGmRbJ8rbL7jAgMB\n" +
        "AAGgYzAXBgkqhkiG9w0BCQcxChMIcGFzc3dvcmQwHAYJKoZIhvcNAQkCMQ8TDW9w\n" +
        "dGlvbmFsIG5hbWUwKgYJKoZIhvcNAQkOMR0wGzAMBgNVHRMBAf8EAjAAMAsGA1Ud\n" +
        "DwQEAwIGQDANBgkqhkiG9w0BAQUFAAOBgQBc7ldGSmyCjMU+ssjglCimqknCVdig\n" +
        "N8FsI/aNRgLqf+eXKWZOxl1v3GB9HCXWDtqOnHd6AJKFpGtK0bqRu7bIncYIiQ1a\n" +
        "P1NW4Kup8d1fTPhw6xgYtxeHvUxRa2y4IXskPUYqp05HavfNZxmcJ5mZOLtgiDIC\n" +
        "I3J80saqEUQKqQ==";

    public static void main(String[] args) throws Exception {
        PKCS10 req = new PKCS10(Base64.getMimeDecoder().decode(csrStr));

        // If PKCS9Attribute did not accept the PrintableString ASN.1 tag,
        // this would fail with an IOException
        Object attr = req.getAttributes().getAttribute("1.2.840.113549.1.9.2");

        // Check that the attribute exists
        if (attr == null) {
            throw new Exception("Attribute should not be null.");
        }

        System.out.println("Test passed.");
    }
}
