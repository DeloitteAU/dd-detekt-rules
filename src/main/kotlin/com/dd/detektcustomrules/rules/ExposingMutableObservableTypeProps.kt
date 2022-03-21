package com.dd.detektcustomrules.rules

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.isProtected
import org.jetbrains.kotlin.psi.psiUtil.isPublic


class ExposingMutableObservableTypeProps(config: Config) : Rule(config) {
    override val issue = Issue(
        javaClass.simpleName,
        Severity.Defect,
        "Avoid exposing observable properties that are mutable such as MutableStateFlow and MutableLiveData",
        Debt.FIVE_MINS,
    )

    private val mutableObservableDataTypes = listOf("MutableLiveData", "MutableStateFlow")

    override fun visitProperty(property: KtProperty) {
        super.visitProperty(property)

        // check if property is accessible from outside of its class
        if (property.isMember && (property.isPublic || property.isProtected())) {
            val dataType = property.typeReference?.text // with declared type
                ?: (property.initializer?.firstChild?.text ?: "") // with inferred type

            if (mutableObservableDataTypes.contains(dataType)) {
                report(CodeSmell(issue = issue, entity = Entity.from(property), message = "${property.name} is accessible from outside of its class and is prone to unpredictable data updates. To restrict write access, ${dataType.replace("Mutable", "")} should be used together with a private $dataType field."))
            }
        }
    }
}
