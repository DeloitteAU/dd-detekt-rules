
package com.dd.detektcustomrules

import com.dd.detektcustomrules.rules.CollapsibleIfStatement
import com.dd.detektcustomrules.rules.IfElseOmittingBraces
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

@Suppress("unused")
class DDRuleSetProvider : RuleSetProvider {
    override val ruleSetId: String = "custom"

    override fun instance(config: Config): RuleSet {
        return RuleSet(
            ruleSetId,
            listOf(
                CollapsibleIfStatement(config),
                IfElseOmittingBraces(config)
            ),
        )
    }
}
