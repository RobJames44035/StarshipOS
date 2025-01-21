/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/**
 * This interface defines a simple standard MBean.
 */
public interface ExceptionThrowerMBean {
    public Exception throwException(int exceptionIndex);
}
