package com.dd.detektcustomrules.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtIfExpression

class CollapsibleIfStatement(config: Config) : Rule(config) {
    override val issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        "Limit the number of redundant nested if statements.",
        Debt.FIVE_MINS,
    )

    override fun visitIfExpression(expression: KtIfExpression) {
        super.visitIfExpression(expression)

        (expression.then as? KtBlockExpression)?.firstStatement?.let {
            if (it is KtIfExpression) {
                report(CodeSmell(issue, Entity.from(expression), "Nested if statements detected. Collapsible nested if statements should be merged"))
            }
        }
    }
}
