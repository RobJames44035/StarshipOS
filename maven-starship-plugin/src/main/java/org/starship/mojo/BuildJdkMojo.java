/*
 *  Copyright (c) 2025 R. A.  and contributors..
 *  This file is part of StarshipOS, an experimental operating system.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 *
 */

package org.starship.mojo;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.starship.util.builders.BuildJDKUtil;

@Mojo(
        name = "build-jdk",
        defaultPhase = LifecyclePhase.COMPILE,
        aggregator = true,
        requiresProject = false
)
public class BuildJdkMojo extends AbstractStarshipMojo {
    @Override
    protected void doExecute() {
        try {
            if(buildJDK) {
                BuildJDKUtil util = new BuildJDKUtil(this);
                if (buildJDK_x86_64) util.buildJDK("x86_64");
                if (buildJDK_ARM) util.buildJDK("arm");
            }
        } catch(Exception e) {
            getLog().error("Failed to build JDK: ", e);
        }
        getLog().info("Built OpenJDK 21 successfully.");
    }
}
