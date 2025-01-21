/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/**
 * Interface TestQueryMBean
 * MBean used for testing the types wired when using QueryExp.
 * It is heavily linked to QueryFactory.
 */
public interface TestQueryMBean
{
    /**
     * Get Att of type boolean
     */
    public boolean getBooleanAtt();

    /**
     * Set Att of type boolean
     */
    public void setBooleanAtt(boolean value);

    /**
     * Get Att of type double
     */
    public double getDoubleAtt();

    /**
     * Set Att of type double
     */
    public void setDoubleAtt(double value);

    /**
     * Get Att of type float
     */
    public float getFloatAtt();

    /**
     * Set Att of type float
     */
    public void setFloatAtt(float value);

    /**
     * Get Att of type int
     */
    public int getIntAtt();

    /**
     * Set Att of type int
     */
    public void setIntAtt(int value);

    /**
     * Get Att of type Integer
     */
    public Integer getIntegerAtt();

    /**
     * Set Att of type Integer
     */
    public void setIntegerAtt(Integer value);

    /**
     * Get Att of type long
     */
    public long getLongAtt();

    /**
     * Set Att of type long
     */
    public void setLongAtt(long value);

    /**
     * Get Att of type String
     */
    public String getStringAtt();

    /**
     * Set Att of type String
     */
    public void setStringAtt(String value);

}
