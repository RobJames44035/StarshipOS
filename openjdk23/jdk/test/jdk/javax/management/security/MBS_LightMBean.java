/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

@SqeDescriptorKey("INTERFACE MBS_LightMBean")
public interface MBS_LightMBean {
    // Getter for property param
    @SqeDescriptorKey("ATTRIBUTE Param")
    public RjmxMBeanParameter getParam() ;

    // Setter for property param
    @SqeDescriptorKey("ATTRIBUTE Param")
    public void setParam(RjmxMBeanParameter param) ;

    // Getter for property aString
    @SqeDescriptorKey("ATTRIBUTE Astring")
    public String getAstring() ;

    // Setter for property aString
    @SqeDescriptorKey("ATTRIBUTE Astring")
    public void setAstring(String aString) ;

    // Getter for property anInt
    @SqeDescriptorKey("ATTRIBUTE AnInt")
    public int getAnInt() ;

    // Setter for property anInt
    @SqeDescriptorKey("ATTRIBUTE AnInt")
    public void setAnInt(int anInt) ;

    // Getter for property anException
    @SqeDescriptorKey("ATTRIBUTE AnException")
    public Exception getAnException() ;

    // Setter for property anException
    @SqeDescriptorKey("ATTRIBUTE AnException")
    public void setAnException(Exception anException) ;

    // Getter for property anError
    @SqeDescriptorKey("ATTRIBUTE AnError")
    public Error getAnError() ;

    // Setter for property anError
    @SqeDescriptorKey("ATTRIBUTE AnError")
    public void setAnError(Error anError) ;

    // An operation
    @SqeDescriptorKey("OPERATION operate1")
    public RjmxMBeanParameter operate1(
            @SqeDescriptorKey("OPERATION PARAMETER name")String name) ;

    // An operation
    @SqeDescriptorKey("OPERATION operate2")
    public String operate2(
            @SqeDescriptorKey("OPERATION PARAMETER param")RjmxMBeanParameter param) ;

    // Throws an error
    @SqeDescriptorKey("OPERATION throwError")
    public void throwError();

    // Throws an exception
    @SqeDescriptorKey("OPERATION throwException")
    public void throwException() throws Exception;

    // Send a notification
    @SqeDescriptorKey("OPERATION sendNotification")
    public void sendNotification();

    // Receive a notification and return the type
    @SqeDescriptorKey("OPERATION waitForNotification")
    public String waitForNotification();

    // Receive a notification and return the HandBack
    @SqeDescriptorKey("OPERATION waitForNotificationHB")
    public Object waitForNotificationHB();

    // Receive multi notifications and return the HandBacks
    @SqeDescriptorKey("OPERATION waitForMultiNotifications")
    public Object[] waitForMultiNotifications(
            @SqeDescriptorKey("OPERATION PARAMETER nb")String nb);

    // Is the notification received
    @SqeDescriptorKey("OPERATION notificationReceived")
    public Boolean notificationReceived();

    // Return the current authorization Id
    @SqeDescriptorKey("OPERATION getAuthorizationId")
    public String getAuthorizationId();
}
