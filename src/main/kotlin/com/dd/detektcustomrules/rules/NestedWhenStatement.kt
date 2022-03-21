package com.dd.detektcustomrules.rules

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.com.intellij.psi.PsiReference
import org.jetbrains.kotlin.psi.KtWhenEntry
import org.jetbrains.kotlin.psi.KtWhenExpression
import org.jetbrains.kotlin.psi.psiUtil.forEachDescendantOfType

class NestedWhenStatement(config: Config) : Rule(config) {
    override val issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        "When statements should not be nested",
        Debt.FIVE_MINS,
    )

    override fun visitWhenEntry(entry: KtWhenEntry) {
        super.visitWhenEntry(entry)

        val visited = mutableListOf<PsiReference?>()
        entry.forEachDescendantOfType<KtWhenExpression> {x ->
            if (x.reference !in visited) {
                visited.add(x.reference)
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
}
