/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/**
 *
 * Used by AnnotationSecurityTest.java
 **/
public interface UnDescribedMBean {

    public String getStringProp();

    public void setStringProp(String name);

    public void doNothing();

    public void doNothingParam(String name);
}
