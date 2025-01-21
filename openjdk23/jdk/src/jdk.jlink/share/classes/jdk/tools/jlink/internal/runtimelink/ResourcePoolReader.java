/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package jdk.tools.jlink.internal.runtimelink;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import jdk.tools.jlink.internal.runtimelink.JimageDiffGenerator.ImageResource;
import jdk.tools.jlink.plugin.ResourcePool;
import jdk.tools.jlink.plugin.ResourcePoolEntry;

@SuppressWarnings("try")
public class ResourcePoolReader implements ImageResource {

    private final ResourcePool pool;

    public ResourcePoolReader(ResourcePool pool) {
        this.pool = Objects.requireNonNull(pool);
    }

    @Override
    public void close() throws Exception {
        // nothing
    }

    @Override
    public List<String> getEntries() {
        return pool.entries().map(ResourcePoolEntry::path).toList();
    }

    @Override
    public byte[] getResourceBytes(String name) {
        return pool.findEntry(name).orElseThrow().contentBytes();
    }

    @Override
    public InputStream getResource(String name) {
        return pool.findEntry(name).orElseThrow().content();
    }

}
