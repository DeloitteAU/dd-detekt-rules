package com.dd.detektcustomrules.rules

import com.google.common.truth.Truth.assertThat
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.compileAndLint
import org.junit.Test

class CollapsibleIfStatementTest {

    @Test
    fun `Should report nested if statements`() {
        val code = """
        class A {
            fun foo() {
                val x = 1
                val y = 2

                if (x == 1) {
                    if (y == 2) {
                        "this is nested"
                    }
                }
            }
        }
        """

        val findings = CollapsibleIfStatement(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(1)
    }

    @Test
    fun `Should not report nested if statements`() {
        val code = """
        class A {
            fun foo() {
                val x = 1
                val y = 2

                if (x == 1) {
                    val inBetweenIfStatement = "test"
                    if (y == 2) {
                        "this is nested"
                    }
                }
            }
        }
        """

        val findings = CollapsibleIfStatement(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(0)
    }
}
