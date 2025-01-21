/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/scenarios/hotswap/HS301/hs301t005.
 * VM Testbase keywords: [quick, jpda, jvmti, noras, redefine, feature_hotswap]
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jvmti.scenarios.hotswap.HS301.hs301t005.hs301t005
 *
 * @comment compile newclassXX to bin/newclassXX
 * @run driver nsk.share.ExtraClassesBuilder
 *      newclass00
 *
 * @run main/othervm/native
 *      -DLocation=./bin/loadclass
 *      -agentlib:hs301t005=pathToNewByteCode=./bin,-waittime=5,package=nsk,samples=100,mode=compiled
 *      nsk.jvmti.scenarios.hotswap.HS301.hs301t005.hs301t005
 */

package nsk.jvmti.scenarios.hotswap.HS301.hs301t005;
import nsk.share.jvmti.RedefineAgent;
import java.io.*;

public class hs301t005 extends RedefineAgent {
    static {
        location = System.getProperty("Location");
    }
    public static String location;

    public hs301t005(String[] arg) {
        super(arg);
    }


    public static void main(String[] arg) {
        arg = nsk.share.jvmti.JVMTITest.commonInit(arg);

        hs301t005 hsCase = new hs301t005(arg);
        System.exit(hsCase.runAgent());
    }

    public boolean agentMethod() {
        boolean  pass=true;
        try {
            MyClassLoader loader1=  new MyClassLoader(location);
            MyClassLoader loader2 = new MyClassLoader(location);
            Class.forName("nsk.jvmti.scenarios.hotswap.HS301.hs301t005.MyClass",true, loader1);
            MyClass myClass1= new MyClass();
            myClass1.setName("Name");
            Class.forName("nsk.jvmti.scenarios.hotswap.HS301.hs301t005.MyClass",true,loader2);
            MyClass myClass2 = new MyClass();
            myClass2.setName("Name");
            if (myClass1.equals(myClass2)) {
                pass=false;
                System.out.println(" TestCase failed..");
            } else {
                System.out.println(" TestCase passed ");
            }

        }catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return pass;
    }
}

class MyClassLoader extends ClassLoader {
  private String location;

  public MyClassLoader(String location ) {
    super();
    this.location = location;
  }
  public Class loadClass(String name) throws ClassNotFoundException {
      Class cls=null;
      try  {
          cls = super.loadClass(name);
          System.out.println("--"+name+" Class Found in Super Class");
      } catch(Exception exp) {
          if (cls == null) {
              File file = new File(location);
              if ( file.isFile()) {
                  throw new ClassNotFoundException("location ="+location+" is file not directory");
              }
              String  separator = Character.toString(File.separatorChar);
              String fileName = file.getAbsolutePath()+separator+name.replaceAll("\\.",separator)+".class";
              try {
                  FileInputStream fis = new FileInputStream(fileName);
                  int size = fis.available();
                  byte[] bytes = new byte[size];
                  if ( size == fis.read(bytes) ) {
                      cls = defineClass(name, bytes,0,bytes.length);
                  } else {
                      throw new ClassNotFoundException(" [ Class = "+name+"]  file  read erorr.");
                  }
              } catch(FileNotFoundException fnfe) {
                  throw new ClassNotFoundException("Class Failed to get ",fnfe.getCause());
              } catch(ClassFormatError cfe) {
                  throw new ClassNotFoundException("Class Failed to get ",cfe.getCause());
              } catch (java.io.IOException ioe) {
                  throw new ClassNotFoundException(" IOException ",ioe.getCause());
              } finally{
                  if (cls == null) {
                      throw new ClassNotFoundException(" [ Class = "+name+" ]cound not be found in "+location);
                  }
              }
          }
          System.out.println(" [ Class  = "+name+" ] Loaded from SimpleLoader " );
      }
      return cls;
  }

  public String toString() {
    return location;
  }
}
