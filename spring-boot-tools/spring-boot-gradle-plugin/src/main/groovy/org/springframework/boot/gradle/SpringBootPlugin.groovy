/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.compile.Compile
import org.springframework.boot.gradle.agent.AgentPluginFeatures
import org.springframework.boot.gradle.repackage.RepackagePluginFeatures
import org.springframework.boot.gradle.resolve.ResolvePluginFeatures
import org.springframework.boot.gradle.run.RunPluginFeatures

/**
 * Gradle 'Spring Boot' {@link Plugin}.
 *
 * @author Phillip Webb
 * @author Dave Syer
 */
class SpringBootPlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		project.getPlugins().apply(BasePlugin)
		project.getPlugins().apply(JavaPlugin)
		project.getPlugins().apply(ApplicationPlugin)

		project.getExtensions().create("springBoot", SpringBootPluginExtension)

		new AgentPluginFeatures().apply(project)
		new ResolvePluginFeatures().apply(project)
		new RepackagePluginFeatures().apply(project)
		new RunPluginFeatures().apply(project)

		// default to UTF-8 encoding
		project.tasks.withType(Compile).all { t->
			t.doFirst {
				if(!t.options.encoding) {
					t.options.encoding = 'UTF-8'
				}
			}
		}
	}

}