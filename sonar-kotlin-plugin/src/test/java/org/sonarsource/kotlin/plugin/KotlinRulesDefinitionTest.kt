/*
 * SonarSource Kotlin
 * Copyright (C) 2018-2024 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarsource.kotlin.plugin

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.sonar.api.SonarEdition
import org.sonar.api.SonarQubeSide
import org.sonar.api.internal.SonarRuntimeImpl
import org.sonar.api.rule.RuleScope
import org.sonar.api.rules.RuleType
import org.sonar.api.server.rule.RulesDefinition
import org.sonar.api.utils.Version

internal class KotlinRulesDefinitionTest {

    @Test
    fun rules() {
        val repository = repositoryForVersion(Version.create(8, 9))
        Assertions.assertThat(repository!!.name()).isEqualTo("SonarQube")
        Assertions.assertThat(repository.language()).isEqualTo("kotlin")
        val rule = repository.rule("EC518")!!
        Assertions.assertThat(rule.name())
            .isEqualTo("Optimized API: Bluetooth Low-Energy")
        Assertions.assertThat(rule.type()).isEqualTo(RuleType.CODE_SMELL)
        Assertions.assertThat(rule.scope()).isEqualTo(RuleScope.MAIN)
        Assertions.assertThat(rule.htmlDescription()).startsWith("<img src=\"http://www.neomades.com/extern/partage/ecoCode/3sur5_1x.png\">\n" +
                "<p>In contrast to classic Bluetooth,")
    }

    private fun repositoryForVersion(version: Version): RulesDefinition.Repository? {
        val rulesDefinition: RulesDefinition = KotlinRulesDefinition(
            SonarRuntimeImpl.forSonarQube(version, SonarQubeSide.SCANNER, SonarEdition.COMMUNITY))
        val context = RulesDefinition.Context()
        rulesDefinition.define(context)
        return context.repository("kotlin")
    }
}
