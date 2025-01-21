/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8042251
 * @summary Test that inner classes have in its inner classes attribute enclosing classes and its immediate members.
 * @library /tools/lib /tools/javac/lib ../lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox InMemoryFileManager TestResult TestBase
 * @run main InnerClassesHierarchyTest
 */

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

import java.lang.classfile.*;
import java.lang.classfile.attribute.*;
import java.lang.classfile.constantpool.*;

public class InnerClassesHierarchyTest extends TestResult {

    private final Map<String, Set<String>> innerClasses;
    private final String outerClassName;

    public InnerClassesHierarchyTest() throws IOException, ConstantPoolException {
        innerClasses = new HashMap<>();
        outerClassName = InnerClassesHierarchyTest.class.getSimpleName();
        File classDir = getClassDir();
        FilenameFilter filter =
                (dir, name) -> name.matches(outerClassName + ".*\\.class");
        for (File file : Arrays.asList(classDir.listFiles(filter))) {
            ClassModel classFile = readClassFile(file);
            String className = classFile.thisClass().name().stringValue();
            for (PoolEntry pe : classFile.constantPool()) {
                if (pe instanceof ClassEntry classInfo
                        && classInfo.asSymbol().isClassOrInterface()) {
                    String cpClassName = classInfo.asInternalName();
                    if (isInnerClass(cpClassName)) {
                        get(className).add(cpClassName);
                    }
                }
            }
        }
    }

    private boolean isInnerClass(String cpClassName) {
        return cpClassName.contains("$");
    }

    private Set<String> get(String className) {
        if (!innerClasses.containsKey(className)) {
            innerClasses.put(className, new HashSet<>());
        }
        return innerClasses.get(className);
    }

    public static void main(String[] args) throws IOException, ConstantPoolException, TestFailedException {
        new InnerClassesHierarchyTest().test();
    }

    private void test() throws TestFailedException {
        addTestCase("Source file is InnerClassesHierarchyTest.java");
        try {
            Queue<String> queue = new LinkedList<>();
            Set<String> visitedClasses = new HashSet<>();
            queue.add(outerClassName);
            while (!queue.isEmpty()) {
                String currentClassName = queue.poll();
                if (!currentClassName.startsWith(outerClassName)) {
                    continue;
                }
                ClassModel cf = readClassFile(currentClassName);
                InnerClassesAttribute attr = cf.findAttribute(Attributes.innerClasses()).orElse(null);
                checkNotNull(attr, "Class should not contain "
                        + "inner classes attribute : " + currentClassName);
                checkTrue(innerClasses.containsKey(currentClassName),
                        "map contains class name : " + currentClassName);
                Set<String> setClasses = innerClasses.get(currentClassName);
                if (setClasses == null) {
                    continue;
                }
                checkEquals(attr.classes().size(),
                        setClasses.size(),
                        "Check number of inner classes : " + setClasses);
                for (InnerClassInfo info : attr.classes()) {
                    if (!info.innerClass().asSymbol().isClassOrInterface()) continue;
                    String innerClassName = info
                            .innerClass().asInternalName();
                    checkTrue(setClasses.contains(innerClassName),
                            currentClassName + " contains inner class : "
                                    + innerClassName);
                    if (visitedClasses.add(innerClassName)) {
                        queue.add(innerClassName);
                    }
                }
            }
            Set<String> allClasses = innerClasses.entrySet().stream()
                    .flatMap(entry -> entry.getValue().stream())
                    .collect(Collectors.toSet());

            Set<String> a_b = removeAll(visitedClasses, allClasses);
            Set<String> b_a = removeAll(allClasses, visitedClasses);
            checkEquals(visitedClasses, allClasses,
                    "All classes are found\n"
                            + "visited - all classes : " + a_b
                            + "\nall classes - visited : " + b_a);
        } catch (Exception e) {
            addFailure(e);
        } finally {
            checkStatus();
        }
    }

    private Set<String> removeAll(Set<String> set1, Set<String> set2) {
        Set<String> set = new HashSet<>(set1);
        set.removeAll(set2);
        return set;
    }

    public static class A1 {

        public class B1 {
        }

        public enum B2 {
        }

        public interface B3 {
        }

        public @interface B4 {
        }

        public void f() {
            new B1() {
            };
            new B3() {
            };
            new B4() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return null;
                }
            };
            class B5 {
            }
        }

        Runnable r = () -> {
            new B1() {
            };
            new B3() {
            };
            new B4() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return null;
                }
            };
            class B5 {
            }
        };
    }

    public enum A2 {;

        public class B1 {
        }

        public enum B2 {
        }

        public interface B3 {
        }

        public @interface B4 {
        }

        public void a2() {
            new B1() {
            };
            new B3() {
            };
            new B4() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return null;
                }
            };
            class B5 {
            }
        }

        Runnable r = () -> {
            new B1() {
            };
            new B3() {
            };
            new B4() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return null;
                }
            };
            class B5 {
            }
        };
    }

    public interface A3 {

        public class B1 {
        }

        public enum B2 {
        }

        public interface B3 {
        }

        public @interface B4 {
        }

        default void a1() {
            new B1() {
            };
            new B3() {
            };
            new B4() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return null;
                }
            };
            class B5 {
            }
        }

        static void a2() {
            new B1() {
            };
            new B3() {
            };
            new B4() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return null;
                }
            };
            class B5 {
            }
        }
    }

    public @interface A4 {

        public class B1 {
        }

        public enum B2 {
        }

        public interface B3 {
        }

        public @interface B4 {
        }
    }

    {
        new A1() {
            class B1 {
            }

            public void a2() {
                new B1() {
                };
                class B5 {
                }
            }
        };
        new A3() {
            class B1 {
            }

            public void a3() {
                new B1() {
                };
                class B5 {
                }
            }
        };
        new A4() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            class B1 {
            }

            public void a4() {
                new B1() {
                };
                class B5 {
                }
            }
        };
        Runnable r = () -> {
            new A1() {
            };
            new A3() {
            };
            new A4() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return null;
                }
            };
            class B5 {
            }
        };
    }

    static {
        new A1() {
            class B1 {
            }

            public void a2() {
                new B1() {
                };
                class B5 {
                }
            }
        };
        new A3() {
            class B1 {
            }

            public void a3() {
                new B1() {
                };
                class B5 {
                }
            }
        };
        new A4() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            class B1 {
            }

            public void a4() {
                new B1() {
                };
                class B5 {
                }
            }
        };
        Runnable r = () -> {
            new A1() {
            };
            new A3() {
            };
            new A4() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return null;
                }
            };
            class B5 {
            }
        };
    }

    public void a5() {
        class A5 {

            class B1 {
            }

            public void a5() {
                new B1() {
                };

                class B5 {
                }
            }
        }
        Runnable r = () -> {
            new A1() {
            };
            new A3() {
            };
            new A4() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return null;
                }
            };
            class B5 {
            }
        };
    }
}
