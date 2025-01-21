/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package testdata;

public class Pattern3 {

    private String file;

    void troublesCausingMethod() {
        String path = null;
        String query = null;
        this.file = query == null ? path : path + query;
    }
}
