/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.ListResourceBundle;

/**
 * A dummy resource to be loaded by the custom class loader.
 * The contents are unimportant.
 */
public class Bug4179766Resource extends ListResourceBundle {
    public Object[][] getContents() {
        return new Object[][] {
            { "MonthNames",
                new String[] {
                    "January",
                    "February",
                    "March",
                    "April",
                    "May",
                    "June",
                    "July",
                    "August",
                    "September",
                    "October",
                    "November",
                    "December",
                    "",
                }
            },
            { "DayNames",
                new String[] {
                    "Sunday",
                    "Monday",
                    "Tuesday",
                    "Wednesday",
                    "Thursday",
                    "Friday",
                    "Saturday",
                }
            }
        };
    }
}
