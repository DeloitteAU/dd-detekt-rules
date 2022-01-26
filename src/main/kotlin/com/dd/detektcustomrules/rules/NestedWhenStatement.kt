package com.dd.detektcustomrules.rules

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtWhenExpression

class NestedWhenStatement(config: Config) : Rule(config) {
    override val issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        "When statements should not be nested",
        Debt.FIVE_MINS,
    )

    override fun visitWhenExpression(expression: KtWhenExpression) {
        super.visitWhenExpression(expression)

    }
}
