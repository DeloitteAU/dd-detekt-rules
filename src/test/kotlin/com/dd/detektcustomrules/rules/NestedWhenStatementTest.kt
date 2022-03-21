package com.dd.detektcustomrules.rules

import com.google.common.truth.Truth
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.compileAndLint
import org.junit.Test

class NestedWhenStatementTest {

    @Test
    fun `Should not report nested when statement`() {
        val code = """
        class A {
            fun foo() {
                when (1) {
                    0 -> return
                    1 -> return
                    2 -> return
                }
            }
        }
        """

        val findings = NestedWhenStatement(Config.empty).compileAndLint(code)
        Truth.assertThat(findings).hasSize(0)
    }

    @Test
    fun `Should not report nested when statement - compliant code example`() {
        val code = """
        class A {
            fun foo() {
                when (1) {
                    0 -> whenFunction()
                    1 -> return
                }
            }

            private fun whenFunction() {
                when (2) {
                    0 -> return
                    1 -> return
                }
            }
        }
        """

        val findings = NestedWhenStatement(Config.empty).compileAndLint(code)
        Truth.assertThat(findings).hasSize(0)
    }

    @Test
    fun `Should report nested when statement`() {
        val code = """
        class A {
            fun foo() {
                when (1) {
                    0 -> return
                    1 -> when (2) {
                        0 -> return
                        1 -> return
                    }
                }
            }
        }
        """

        val findings = NestedWhenStatement(Config.empty).compileAndLint(code)
        Truth.assertThat(findings).hasSize(1)
    }

    @Test
    fun `Should report nested when statement - two violations`() {
        val code = """
        class A {
            fun foo() {
                when (1) {
                    0 -> when (0) {
                        0 -> return
                        1 -> return
                    }
                    1 -> when (2) {
                        0 -> return
                        1 -> return
                    }
                }
            }
        }
        """

        val findings = NestedWhenStatement(Config.empty).compileAndLint(code)
        Truth.assertThat(findings).hasSize(2)
    }

    @Test
    fun `Should report nested when statement - nested within a nested statement`() {
        val code = """
        class A {
            fun foo() {
                when (1) {
                    0 -> return
                    1 -> when (2) { // violation #1
                        0 -> when (3) { // violation #2
                            0 -> return
                            1 -> return
                        }
                        1 -> return
                    }
                }
            }
        }
        """

        val findings = NestedWhenStatement(Config.empty).compileAndLint(code)
        Truth.assertThat(findings).hasSize(2)
    }

    @Test
    fun `Should report nested when statement - 3 layer nested`() {
        val code = """
        class A {
            fun foo() {
                when (1) {
                    0 -> return
                    1 -> when (2) { // violation #1
                        0 -> when (3) { // violation #2
                            0 -> when (4) { // violation #3
                                0 -> return
                                1 -> return
                            }
                            1 -> return
                        }
                        1 -> return
                    }
                }
            }
        }
        """

        val findings = NestedWhenStatement(Config.empty).compileAndLint(code)
        Truth.assertThat(findings).hasSize(3)
    }

    @Test
    fun `Should report nested when statement - mixed cases`() {
        val code = """
        class A {
            fun foo() {
                when (1) {
                    0 -> return
                    1 -> when (2) { // violation #1
                        0 -> when (3) { // violation #2
                            0 -> when (4) { // violation #3
                                0 -> return
                                1 -> return
                            }
                            1 -> return
                        }
                        1 -> when (4) { // violation #4
                            0 -> return
                            1 -> return
                        }
                    }
                }
            }
        }
        """

        val findings = NestedWhenStatement(Config.empty).compileAndLint(code)
        Truth.assertThat(findings).hasSize(4)
    }
}
