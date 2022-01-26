package com.dd.detektcustomrules.rules

import com.google.common.truth.Truth.assertThat
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.compileAndLint
import org.junit.Test

class ExpressionFunctionsTest {

    @Test
    fun `should report when function contains a single expression is not represented as function expression`() {
        val code = """
            fun singleExpression(): String {
                return "Hello World"
            }
        """

        val findings = ExpressionFunctions(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(1)
    }

    @Test
    fun `should not report if function does not contain single expression`() {
        val code ="""
           fun multipleLines(): Int {
                val x = 1
                val y = 2
                return x + y
           } 
        """

        val findings = ExpressionFunctions(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(0)
    }

    @Test
    fun `should not report if function with single expression is already represented as expression function`() {
        val code ="""
           fun expressionFunction() = "This is an expression function" 
        """

        val findings = ExpressionFunctions(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(0)
    }
}
