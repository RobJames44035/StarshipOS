/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

import java.security.Principal;
import java.util.List;

import javax.management.remote.JMXServiceURL ;
import javax.management.ObjectName;

@SqeDescriptorKey("INTERFACE ServerDelegateMBean")
public interface ServerDelegateMBean {
    @SqeDescriptorKey("ATTRIBUTE Address")
    public void addAddress(JMXServiceURL url);

    @SqeDescriptorKey("ATTRIBUTE Address")
    public List<JMXServiceURL> getAddresses();

    public String getPort();
    public void setPort(String p);

    public String getJavaVersion();

    public void sqeJmxwsCredentialsProviderCalled();
    public int getSqeJmxwsCredentialsProviderCallCount();

    public void setJmxwsCredentialsProviderUrl(String url);
    public String getJmxwsCredentialsProviderUrl();

    public void testJMXAuthenticatorCalled();
    public int getTestJMXAuthenticatorCallCount();

    public void setTestJMXAuthenticatorPrincipal(Principal principal);
    public String getTestJMXAuthenticatorPrincipalString();

    public void createStandardMBean(
            String implementationClassName,
            String interfaceClassName,
            boolean isMXBean,
            ObjectName name)
            throws Exception;

    public void createStandardMBean(
            String implementationClassName,
            boolean isMXBean,
            ObjectName name)
            throws Exception;
}
