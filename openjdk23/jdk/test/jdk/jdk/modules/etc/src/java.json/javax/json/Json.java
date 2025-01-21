/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package javax.json;

import java.io.InputStream;

public class Json {
    private Json() { }

    public static JsonParser createParser(InputStream in) {
        return new JsonParser() { };
    }
}
