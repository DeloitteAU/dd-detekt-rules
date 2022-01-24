package com.dd.detektcustomrules.rules

import com.google.common.truth.Truth.assertThat
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.compileAndLint
import org.junit.Test

class WhenClauseLinesOfCodeTest {

    @Test
    fun `Should report when clause has too many lines`() {
        val code = """
        class A {
            fun foo() {
                val x = 2
                when (x) {
                    0 -> {
                        methodCall(0)
                        methodCall(1)
                        methodCall(2)
                        methodCall(3)
                    }
                    1 -> return
                }
            }

            private fun methodCall (num : Int) {
                print(num)
            }
        }
        """

        val findings = WhenClauseLinesOfCode(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(1)
    }

    @Test
    fun `Should report when clause has too many lines (3 violations)`() {
        val code = """
        class A {
            fun foo() {
                val x = 5
                when (x) {
                    0 -> { // 6 lines until next clause
                        methodCall(0)
                        methodCall(1)
                        methodCall(2)
                        methodCall(3)
                    } 
                    1 -> methodCall(24)
                    2 -> { // 6 lines until next clause
                        methodCall(4)
                        methodCall(5)
                        methodCall(6)
                        methodCall(7)
                    }
                    3 -> methodCall(42)
                    4 -> { // 6 lines until next clause
                        methodCall(8)
                        methodCall(9)
                        methodCall(10)
                        methodCall(11)
                    }
                }
            }

            private fun methodCall (num : Int) {
                print(num)
            }
        }
        """

        val findings = WhenClauseLinesOfCode(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(3)
    }

    @Test
    fun `Should not report when clause has too many lines`() {
        val code = """
        class A {
            fun foo() {
                val x = 3
                when (x) {
                    0 -> methodCall(9)
                    1 -> methodCall(24)
                    2 -> return
                }
            }

            private fun methodCall (num : Int) {
                print(num)
            }
        }
        """

        val findings = WhenClauseLinesOfCode(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(0)
    }

    @Test
    fun `Should not report when clause has too many lines (5 lines until next clause)`() {
        val code = """
        class A {
            fun foo() {
                val x = 3
                when (x) {
                    0 -> { // 5 lines until next clause
                        methodCall(0)
                        methodCall(1)
                        methodCall(2)
                    }
                    1 -> { // 4 lines until next clause
                        methodCall(3)
                        methodCall(4)
                    } 
                    2 -> { // 5 lines until next clause
                        methodCall(5)
                        methodCall(6)
                        methodCall(7)
                    } 
                }
            }

            private fun methodCall (num : Int) {
                print(num)
            }
        }
        """

        val findings = WhenClauseLinesOfCode(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(0)
    }

}
