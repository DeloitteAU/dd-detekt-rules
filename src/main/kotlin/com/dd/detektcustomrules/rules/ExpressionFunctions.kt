package com.dd.detektcustomrules.rules

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtReturnExpression

class ExpressionFunctions(config: Config) : Rule(config) {
    override val issue = Issue(
        javaClass.simpleName,
        Severity.Style,
        "When a function contains only a single expression it can be represented as an expression function",
        Debt.FIVE_MINS
    )

    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)

        function.bodyBlockExpression?.statements?.let { expressions ->
            if (expressions.size == 1 && expressions.first() is KtReturnExpression) {
                report(
                    CodeSmell(
                        issue = issue,
                        entity = Entity.from(function),
                        message = "${function.name} only contains a single expression and can be represented as an expression function"
                    )
                )
            }

        }
    }

}
