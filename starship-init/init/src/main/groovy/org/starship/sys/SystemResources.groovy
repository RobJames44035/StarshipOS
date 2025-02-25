/*
 *
 *  *
 *  * StarshipOS $file.filename Copyright (c) 2025 R. A. James
 *  * UPDATED: 2/25/25, 1:18 PM by rajames
 *  *
 *  * StarshipOS is licensed under GPL2, GPL3, Apache 2
 *  *
 *
 *
 */

package org.starship.sys

import java.util.concurrent.ConcurrentHashMap

class SystemResources implements Serializable {
    private static final long serialVersionUID = 1L

    ConcurrentHashMap<String, Object> processTable = new ConcurrentHashMap<>()
    ConcurrentHashMap<String, Object> resourceTable = new ConcurrentHashMap<>()
    ConcurrentHashMap<String, Object> serviceTable = new ConcurrentHashMap<>()

    private static final SystemResources INSTANCE = new SystemResources()

    private SystemResources() {
        // Private constructor to enforce singleton
    }

    static SystemResources getInstance() {
        return INSTANCE
    }
}
