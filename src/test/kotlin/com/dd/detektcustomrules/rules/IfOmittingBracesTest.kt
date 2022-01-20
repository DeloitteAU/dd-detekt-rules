package com.dd.detektcustomrules.rules

import com.google.common.truth.Truth.assertThat
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.compileAndLint
import org.junit.Test

class IfOmittingBracesTest {

    @Test
    fun `Doesn't need braces`() {
        val code = """
        class A {
            fun foo() {
                val x = 1
                if (x == 1) return
            }
        }
        """
        val findings = IfOmittingBraces(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(0)
    }

    @Test
    fun `Has braces and is fine`() {
        val code = """
        class A {
            fun foo() {
                val x = 1
                if (x == 1) {
                    return
                }
            }
        }
        """
        val findings = IfOmittingBraces(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(0)
    }

    @Test
    fun `Must have braces because of newline`() {
        val code = """
        class A {
            fun foo() {
                val x = 1
                if (x == 1) 
                return
            }
        }
        """
        val findings = IfOmittingBraces(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(1)
    }
    @Test
    fun `Must have braces because line is too long`() {
        val code = """
        class A {
            fun foo() {
                val x = 1
                if (x == 1) return 39248091481392480914813924809148139248091481392480914813924809148139248091481392480914813924809148139248091481
            }
        }
        """
        val findings = IfOmittingBraces(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(1)
    }
}
