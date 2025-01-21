/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package jdk.test.lib.artifacts;

import java.nio.file.Path;
import java.util.Map;

public interface ArtifactManager {
    public Path resolve(Artifact artifact) throws ArtifactResolverException;
    default public Path resolve(String name, Map<String, Object> artifactDescription,
                        boolean unpack) throws ArtifactResolverException {
        throw new ArtifactResolverException("not implemented");
    }
}
