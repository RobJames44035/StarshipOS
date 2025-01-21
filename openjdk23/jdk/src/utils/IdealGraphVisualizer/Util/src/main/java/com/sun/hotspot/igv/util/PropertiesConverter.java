/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package com.sun.hotspot.igv.util;

import com.sun.hotspot.igv.data.Properties;
import com.sun.hotspot.igv.data.Property;

/**
 *
 * @author ksakata
 */
public class PropertiesConverter {
    public static String convertToHTML(final Properties properties) {
        StringBuilder sb = new StringBuilder("<html><body><table cellpadding=\"0\" cellspacing=\"0\">");
        for (Property p : properties) {
            sb.append("<tr><td>")
              .append(StringUtils.escapeHTML(p.getName()))
              .append("</td><td width=\"10\"></td><td>")
              .append(StringUtils.escapeHTML(p.getValue()))
              .append("</td></tr>");
        }
        sb.append("</table></body></html>");
        return sb.toString();
    }
}
