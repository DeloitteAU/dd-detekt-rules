package com.dd.detektcustomrules.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtIfExpression

class IfOmittingBraces(config: Config) : Rule(config) {
    override val issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        "if/else conditional that is used as an expression may omit " +
            "braces only if the entire expression fits on one line.",
        Debt.FIVE_MINS
    )

    override fun visitIfExpression(expression: KtIfExpression) {
        super.visitIfExpression(expression)
        val expressionFitsInOneLine = fitsInOneLine(expression)
        val hasNewLine = hasNewLine(expression)
        if (expression.then !is KtBlockExpression && (!expressionFitsInOneLine || hasNewLine)) {
            report(
                CodeSmell(
                    issue,
                    Entity.from(expression),
                    "no braces detected for if else statement, place on one line if it fits else add braces"
                )
            )
        }
    }

    private fun fitsInOneLine(line: KtIfExpression): Boolean {
        val ifStatementLength = line.textLength
        if (ifStatementLength > defaultLineLength) {
            return false
        }
        val x = 1
        return true
    }

    private fun hasNewLine(line: KtIfExpression): Boolean {
        val ifExpressionString = line.text
        if (ifExpressionString.contains('\n')) {
            return true
        }
        return false
    }

    companion object {
        private const val defaultLineLength = 120
    }
}
