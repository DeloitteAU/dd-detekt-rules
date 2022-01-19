package com.dd.detektcustomrules.rules

import com.google.common.truth.Truth.assertThat
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.compileAndLint
import org.junit.Test

class ExposingMutableObservableTypePropsTest {

    @Test
    fun `should report when MutableLiveData property (with inferred type) is exposed`() {
        val code = """
            class A {
                 val state = MutableLiveData(ViewModelState())
            }
        """
        val findings = ExposingMutableObservableTypeProps(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(1)
    }

    @Test
    fun `should report when MutableLiveData property (with declared type) is exposed`() {
        val code = """
            class A {
                 val state: MutableLiveData = MutableLiveData(ViewModelState())
            }
        """
        val findings = ExposingMutableObservableTypeProps(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(1)
    }

    @Test
    fun `should report when MutableStateFlow property is exposed`() {
        val code = """
            class A {
                 val state = MutableStateFlow(ViewModelState())
            }
        """
        val findings = ExposingMutableObservableTypeProps(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(1)
    }

    @Test
    fun `should not report when MutableLiveData property is not accessible from outside of the class`() {
        val code = """
            class A {
                 private val state: MutableLiveData = MutableLiveData(ViewModelState())
            }
        """
        val findings = ExposingMutableObservableTypeProps(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(0)
    }

    @Test
    fun `should not report MutableLiveData variables`() {
        val code = """
            class A {
                fun test() {
                    val state = MutableLiveData(ViewModelState())
                }
            }
        """
        val findings = ExposingMutableObservableTypeProps(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(0)
    }
}
