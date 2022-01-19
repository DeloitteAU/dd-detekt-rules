package com.dd.detektcustomrules.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.api.Issue
import org.jetbrains.kotlin.psi.KtFile


class FormattingBraces(config: Config) : Rule(config) {

    override val issue = Issue(
        javaClass.simpleName,
        Severity.Style,
        "Braces are not required for when branches and if statement bodies which have no else if/else branches and which fit on a single line.",
        Debt.FIVE_MINS,
    )

//    override fun formatBraces(expression:KtFormatBraces)
}
