/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

public interface ClassLoaderAware {
    public ClassLoader getContextClassLoader();
    public void checkMemberAccess(Class<?> clazz, int which);
}
