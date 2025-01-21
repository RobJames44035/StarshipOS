/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.util.Collection;
import java.util.stream.Collectors;

public enum  ClassType {
    CLASS("class"),
    INTERFACE("interface") {
        @Override
        public String methodToString(TestCase.TestMethodInfo method) {
            String modifiers = method.mods.stream()
                    .collect(Collectors.joining(" "));
            boolean hasBody = modifiers.contains("static") || modifiers.contains("default");
            String parameters = method.parameters.stream()
                    .map(TestCase.TestMemberInfo::generateSource)
                    .collect(Collectors.joining(", "));
            return String.format("%s %s %s(%s) %s",
                    method.indention() + modifiers,
                    "int",
                    method.getName(),
                    parameters,
                    hasBody ? "{return 0;}" : ";");
        }
    },
    ANNOTATION("@interface") {
        @Override
        public String methodToString(TestCase.TestMethodInfo method) {
            String modifiers = method.mods.stream()
                    .collect(Collectors.joining(" "));
            return String.format("%s %s %s() %s",
                    method.indention() + modifiers,
                    "int",
                    method.getName(),
                    ";");
        }
    },
    ENUM("enum") {
        @Override
        public String fieldToString(TestCase.TestFieldInfo field) {
            return field.indention() + field.name;
        }

        @Override
        public String collectFields(Collection<TestCase.TestFieldInfo> fields) {
            return fields.stream()
                    .map(TestCase.TestMemberInfo::generateSource)
                    .collect(Collectors.joining(",\n")) + ";\n";
        }
    };

    private final String classType;

    ClassType(String classType) {
        this.classType = classType;
    }

    private String collectSrc(Collection<? extends TestCase.TestMemberInfo> members) {
        String src = members.stream()
                .map(TestCase.TestMemberInfo::generateSource)
                .collect(Collectors.joining("\n"));
        return src.trim().isEmpty() ? "" : src + "\n\n";
    }

    public String collectInnerClasses(Collection<TestCase.TestClassInfo> innerClasses) {
        return collectSrc(innerClasses);
    }

    public String collectFields(Collection<TestCase.TestFieldInfo> fields) {
        return collectSrc(fields);
    }

    public String collectMethods(Collection<TestCase.TestMethodInfo> methods) {
        return collectSrc(methods);
    }

    public String methodToString(TestCase.TestMethodInfo method) {
        String modifiers = method.mods.stream()
                .collect(Collectors.joining(" "));
        String parameters = method.parameters.stream()
                .map(TestCase.TestMemberInfo::generateSource)
                .collect(Collectors.joining(", "));
        String localClasses = collectInnerClasses(method.localClasses.values());
        String methodBody = modifiers.contains("abstract") ? ";" :
                String.format("{%n%s%s%n%s}",
                        localClasses,
                        method.isConstructor
                                ? ""
                                : method.indention() + "return false;",
                        method.indention());
        return String.format("%s %s %s(%s) %s",
                method.indention() + modifiers,
                method.isConstructor ? "" : "boolean",
                method.getName(),
                parameters,
                methodBody);
    }

    public String fieldToString(TestCase.TestFieldInfo field) {
        String modifiers = field.mods.stream()
                .collect(Collectors.joining(" "));
        return String.format("%s int %s = 0;",
                field.indention() + modifiers,
                field.name);
    }

    public String getDescription() {
        return classType;
    }
}
