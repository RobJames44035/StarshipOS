/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.management.remote.JMXServiceURL ;
import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

/**
 * This class defines an MBean that can be registered and used on client side
 * to handle informations or properties of the remote server.
 *
 * For example, this MBean can store IOR addresses
 * of RMI/IIOP connector(s) used in a test.
 *
 * That MBean might not be used for testing purpose itself.
 */
public class ServerDelegate implements ServerDelegateMBean, MBeanRegistration {

    private MBeanServer mbeanServer = null;
    private List<JMXServiceURL> addresses  = null;
    private String port;
    private static String javaVersion = System.getProperty("java.version");
    private int sqeJmxwsCredentialsProviderCallCount = 0;
    private String jmxwsCredentialsProviderUrl = null;
    private int testJMXAuthenticatorCallCount = 0;
    private Principal testJMXAuthenticatorPrincipal = null;

    @SqeDescriptorKey("NO PARAMETER CONSTRUCTOR ServerDelegate")
    public ServerDelegate() {
        addresses = new ArrayList<JMXServiceURL>();
    }

    public ObjectName preRegister(MBeanServer server, ObjectName name)
    throws Exception {
        // Initialize MBeanServer attribute
        mbeanServer = server;
        return name;
    }
    public void postRegister(Boolean registrationDone) {
    }
    public void preDeregister() throws Exception {
    }
    public void postDeregister() {
    }

    public void addAddress(JMXServiceURL url) {
        addresses.add(url) ;
    }

    public List<JMXServiceURL> getAddresses() {
        return addresses ;
    }

    public void setPort(String p) {
        port = p ;
    }

    public String getPort() {
        return port ;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void sqeJmxwsCredentialsProviderCalled() {
        sqeJmxwsCredentialsProviderCallCount++;
    }

    public int getSqeJmxwsCredentialsProviderCallCount() {
        return sqeJmxwsCredentialsProviderCallCount;
    }

    public void setJmxwsCredentialsProviderUrl(String url) {
        jmxwsCredentialsProviderUrl = url;
    }

    public String getJmxwsCredentialsProviderUrl() {
        return jmxwsCredentialsProviderUrl;
    }

    public void testJMXAuthenticatorCalled() {
        testJMXAuthenticatorCallCount++;
    }

    public int getTestJMXAuthenticatorCallCount() {
        return testJMXAuthenticatorCallCount;
    }

    public void setTestJMXAuthenticatorPrincipal(Principal principal) {
        testJMXAuthenticatorPrincipal = principal;
    }

    public String getTestJMXAuthenticatorPrincipalString() {
        if ( testJMXAuthenticatorPrincipal != null ) {
            return testJMXAuthenticatorPrincipal.toString();
        }

        return null;
    }

   /**
     * Instantiates and registers a StandardMBean in the MBean server.
     *
     * @param implementationClassName
     *      The implementation class name of the MBean.
     * @param interfaceClassName
     *      The management interface class name of the MBean.
     * @param isMXBean
     *      If true, the resultant MBean is an MXBean.
     * @param name
     *      The object name of the StandardMBean.
     */
    @SuppressWarnings("unchecked")
    public void createStandardMBean(
            String implementationClassName,
            String interfaceClassName,
            boolean isMXBean,
            ObjectName name)
            throws Exception {

        Object implementation =
                Class.forName(implementationClassName).newInstance();
        Class<Object> interfaceClass = interfaceClassName == null ? null :
            (Class<Object>)Class.forName(interfaceClassName);

        // Create the StandardMBean
        StandardMBean standardMBean = new StandardMBean(
                implementation,
                interfaceClass,
                isMXBean);

        // Register the StandardMBean
        mbeanServer.registerMBean(standardMBean, name);
    }

    /**
     * Instantiates and registers a StandardMBean in the MBean server.
     * The object will use standard JMX design pattern to determine
     * the management interface associated with the given implementation.
     */
    @SuppressWarnings("unchecked")
    public void createStandardMBean(
            String implementationClassName,
            boolean isMXBean,
            ObjectName name)
            throws Exception {

        createStandardMBean(implementationClassName, null, isMXBean, name);
    }
}
