/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4075040
 * @summary Make sure the Mimetable is initialized
 *    at first use.
 */

import java.net.*;

public class GetFileNameMap {
    public static void main(String[] args) throws Exception {
        FileNameMap map = URLConnection.getFileNameMap();
        String s = map.getContentTypeFor("test.pdf");
    }
}
