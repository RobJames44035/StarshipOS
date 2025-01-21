/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test 1.1, 06/11/07
 * @author Xuelei Fan
 * @bug 6466247
 * @summary java.security.debug permission=<classname> and codebase=<URL>
 *          options do not work
 * @modules java.base/sun.security.util
 * @run main/othervm -Djava.security.debug="stacknothing--=-30logincontextacCess:stack-domain,combiner;access:fAilure-jarpermission=sun.dummy.DummyPermission;peRmiSsion=sun.Dummy.DummyPermission2=permission=sun.dummy.DummyPermission3:codEbAse=/dir1/DIR2/Dir3/File.java,codebase=http://www.sun.com/search?q=SunMicro,codEbAse=/dir1/DIR2/Dir3/File.java;coDebase=www.sun.com;codebase=file:///C:/temp/foo%20more/a.txt" MultiOptions
 */
import sun.security.util.Debug;

public class MultiOptions
{
    public static void main(String args[]) throws Exception {

        if (!Debug.isOn("access") ||
                !Debug.isOn("stack") ||
                !Debug.isOn("logincontext") ||
                !Debug.isOn("domain") ||
                !Debug.isOn("combiner") ||
                !Debug.isOn("failure") ||
                !Debug.isOn("jar") ||
                !Debug.isOn("permission=sun.dummy.DummyPermission") ||
                Debug.isOn("permission=sun.dummy.dummypermission") ||
                !Debug.isOn("permission=sun.Dummy.DummyPermission2") ||
                !Debug.isOn("permission=sun.dummy.DummyPermission3") ||
                !Debug.isOn("codebase=/dir1/DIR2/Dir3/File.java") ||
                Debug.isOn("codebase=/dir1/dir2/dir3/file.java") ||
                !Debug.isOn("codebase=www.sun.com") ||
                !Debug.isOn("codebase=file:///C:/temp/foo%20more/a.txt") ||
                !Debug.isOn("codebase=http://www.sun.com/search?q=SunMicro") ) {
            throw new Exception("sun.security.Debug failed to parse options");
        }
    }

}
