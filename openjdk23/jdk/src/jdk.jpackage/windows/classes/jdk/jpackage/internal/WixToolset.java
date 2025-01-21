/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package jdk.jpackage.internal;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class WixToolset {

    static enum WixToolsetType {
        // Wix v4+
        Wix4(WixTool.Wix4),
        // Wix v3+
        Wix3(WixTool.Candle3, WixTool.Light3);

        WixToolsetType(WixTool... tools) {
            this.tools = Set.of(tools);
        }

        Set<WixTool> getTools() {
            return tools;
        }

        private final Set<WixTool> tools;
    }

    private WixToolset(Map<WixTool, WixTool.ToolInfo> tools) {
        this.tools = tools;
    }

    WixToolsetType getType() {
        return Stream.of(WixToolsetType.values()).filter(toolsetType -> {
            return toolsetType.getTools().equals(tools.keySet());
        }).findAny().get();
    }

    Path getToolPath(WixTool tool) {
        return tools.get(tool).path;
    }

    DottedVersion getVersion() {
        return tools.values().iterator().next().version;
    }

    static WixToolset create(Set<WixTool> requiredTools, Map<WixTool, WixTool.ToolInfo> allTools) {
        var filteredTools = allTools.entrySet().stream().filter(e -> {
            return requiredTools.contains(e.getKey());
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (filteredTools.keySet().equals(requiredTools)) {
            return new WixToolset(filteredTools);
        } else {
            return null;
        }
    }

    private final Map<WixTool, WixTool.ToolInfo> tools;
}
