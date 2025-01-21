/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
/*
 * @test
 * @bug 6356571
 * @summary Make sure that non-ResourceBundle classes are ignored and
 * properties files are loaded correctly.
 */

import java.util.ResourceBundle;

public class Bug6356571 {
    // Bug6356571.class is not a ResourceBundle class, so it will be
    // ignored and Bug6356571.properties will be loaded.
    private ResourceBundle bundle = ResourceBundle.getBundle("Bug6356571");

    void check() {
        String id = bundle.getString("id");
        if (!"6356571".equals(id)) {
            throw new RuntimeException("wrong id: " + id);
        }
    }

    public static final void main(String[] args) {
        new Bug6356571().check();
    }
}
