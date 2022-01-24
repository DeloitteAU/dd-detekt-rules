package com.dd.detektcustomrules.rules

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtWhenEntry

class WhenClauseLinesOfCode(config: Config) : Rule(config) {
    override val issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        "When clause has too many lines of code until next clause",
        Debt.FIVE_MINS,
    )

    override fun visitWhenEntry(entry: KtWhenEntry) {
        super.visitWhenEntry(entry)

        val newLines = getNewLineSizeOfWhenEntry(entry)

        if (newLines > 5) {
            report(CodeSmell(issue, Entity.from(entry),
                "When entry contains " + newLines +
                    "of lines of code until next entry. " +
                    "Please reduce to 5 lines."))
            return
        }
    }


    private fun getNewLineSizeOfWhenEntry(entry : KtWhenEntry) : Int {
        // Filter new line size does not count end of text i.e. the last new line
        return entry.text.filter{it == '\n'}.count() + 1
    }

}
