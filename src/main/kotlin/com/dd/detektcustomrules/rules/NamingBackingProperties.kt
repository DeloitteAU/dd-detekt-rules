package com.dd.detektcustomrules.rules

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.isPrivate
import org.jetbrains.kotlin.psi.psiUtil.isPublic

class NamingBackingProperties(config: Config) : Rule(config) {
    override val issue = Issue(
        javaClass.simpleName,
        Severity.Style,
        "Backing property name should exactly match that of the real property except prefixed with an underscore.",
        Debt.FIVE_MINS,
    )

    override fun visitClass(klass: KtClass) {
        super.visitClass(klass)

        val properties = klass.getProperties()
        val fields = properties.filterPrivate()
        val propsWithGetter = properties.filterPublicWithGetter()

        for (prop in propsWithGetter) {
            val backingFields = fields.filter {
                prop.getter?.text?.contains(it.name ?: "") ?: false
            }

            for (field in backingFields) {
                val fieldName = field.name ?: ""

                // Report violation if fieldName isn't prefixed with underscore
                // or it doesn't exactly match with the real property name
                if (!fieldName.startsWith("_") || fieldName.removePrefix("_") != prop.name) {
                    report(
                        CodeSmell(
                            issue = issue,
                            entity = Entity.atName(field),
                            message = issue.description
                        )
                    )
                }
            }
        }
    }
}

private fun List<KtProperty>.filterPrivate() =
    this.filter { it.isPrivate() }

private fun List<KtProperty>.filterPublicWithGetter() =
    this.filter { it.isPublic && it.getter != null }
