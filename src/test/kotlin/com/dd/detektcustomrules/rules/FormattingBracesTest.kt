package com.dd.detektcustomrules.rules

import com.google.common.truth.Truth.assertThat
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.compileAndLint
import org.junit.Test

class FormattingBracesTest {

    @Test
    fun `Can not have braces`(){
        val code = """
        class A {
            fun foo() {
                val x = 1

                if (x == 1) return
            }
        }
        """
        val findings = FormattingBraces(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(1)
    }


    @Test
    fun `Needs to have braces 1`(){
        val code = """
        class A {
            fun foo() {
                val x = 1

                if (x == 1) return
                
                else return
            }
        }
        """
        val findings = FormattingBraces(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(1)
    }
}
