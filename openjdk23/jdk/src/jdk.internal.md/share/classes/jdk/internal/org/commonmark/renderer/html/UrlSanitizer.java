/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, a notice that is now available elsewhere in this distribution
 * accompanied the original version of this file, and, per its terms,
 * should not be removed.
 */

package jdk.internal.org.commonmark.renderer.html;

import jdk.internal.org.commonmark.node.Image;
import jdk.internal.org.commonmark.node.Link;

/**
 * Sanitizes urls for img and a elements by whitelisting protocols.
 * This is intended to prevent XSS payloads like [Click this totally safe url](javascript:document.xss=true;)
 * <p>
 * Implementation based on https://github.com/OWASP/java-html-sanitizer/blob/f07e44b034a45d94d6fd010279073c38b6933072/src/main/java/org/owasp/html/FilterUrlByProtocolAttributePolicy.java
 *
 * @since 0.14.0
 */
public interface UrlSanitizer {
    /**
     * Sanitize a url for use in the href attribute of a {@link Link}.
     *
     * @param url Link to sanitize
     * @return Sanitized link
     */
    String sanitizeLinkUrl(String url);

    /**
     * Sanitize a url for use in the src attribute of a {@link Image}.
     *
     * @param url Link to sanitize
     * @return Sanitized link {@link Image}
     */
    String sanitizeImageUrl(String url);
}
