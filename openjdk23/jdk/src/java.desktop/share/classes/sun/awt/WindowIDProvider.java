/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package sun.awt;

/**
 * Classes implementing this interface have public getWindow method,
 * returning handle to the native window.
 */
public interface WindowIDProvider {
    public long getWindow();
}
