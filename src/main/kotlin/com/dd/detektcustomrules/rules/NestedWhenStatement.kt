package com.dd.detektcustomrules.rules

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtWhenExpression
import org.jetbrains.kotlin.psi.psiUtil.collectDescendantsOfType
import org.jetbrains.kotlin.psi.psiUtil.findDescendantOfType
import org.jetbrains.kotlin.psi.psiUtil.forEachDescendantOfType

class NestedWhenStatement(config: Config) : Rule(config) {
    override val issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        "When statements should not be nested",
        Debt.FIVE_MINS,
    )

    override fun visitWhenExpression(expression: KtWhenExpression) {
        super.visitWhenExpression(expression)

//        val nested = expression.collectDescendantsOfType<KtWhenExpression>()
//        for (st in nested) {
//            report(
//                CodeSmell(
//                    issue,
//                    Entity.from(st),
//                    "Hello"
//                )
//            )
//        }

        expression.forEachDescendantOfType<KtWhenExpression> {x ->
            report(
                CodeSmell(
                    issue,
                    Entity.from(x),
                    "Nested when statements detected. " +
                            "Please move nested when statement to a new function."
                )
            )
        }

    }
}
