/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
import java.util.ListResourceBundle;

public class Bug4396021SpecialMessages extends ListResourceBundle {

    private static final Object[][] contents = {
        {"special_key", "special_value"},
    };

    public Bug4396021SpecialMessages() {
        setParent(getBundle("Bug4396021GeneralMessages"));
    }

    protected Object[][] getContents() {
        return contents;
    }
}
