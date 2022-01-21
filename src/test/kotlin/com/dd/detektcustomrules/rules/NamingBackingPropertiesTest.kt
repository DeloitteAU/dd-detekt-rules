package com.dd.detektcustomrules.rules

import com.google.common.truth.Truth.assertThat
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.test.compileAndLint
import org.junit.Test

class NamingBackingPropertiesTest {

    @Test
    fun `should report if the name of the backing property doesn't exactly match that of the real property`() {
        val code = """
            class A {
                private var _name: Map? = null
 
                val table: Map
                    get() {
                        if (_name == null) {
                            _name = HashMap()
                        }
                        return _name ?: throw AssertionError()
                    }
            }
        """

        val findings = NamingBackingProperties(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(1)
    }

    @Test
    fun `should report if the name of the backing property is not prefixed with underscore`() {
        val code = """
            class A {
                private var mTable: Map? = null
 
                val table: Map
                    get() {
                        if (mTable == null) {
                            mTable = HashMap()
                        }
                        return mTable ?: throw AssertionError()
                    }
            }
        """

        val findings = NamingBackingProperties(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(1)
    }

    @Test
    fun `should not report if the name exactly matches that of the real property and is prefixed with underscore`() {
        val code = """
            class A {
                private var _table: Map? = null
 
                val table: Map
                    get() {
                        if (_table == null) {
                            _table = HashMap()
                        }
                        return _table ?: throw AssertionError()
                    }
            }
        """

        val findings = NamingBackingProperties(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(0)
    }

    @Test
    fun `should report if the name exactly matches that of the real property but with different casing`() {
        val code = """
            class A {
                private var _Table: Map? = null
 
                val table: Map
                    get() {
                        if (_Table == null) {
                            _Table = HashMap()
                        }
                        return _Table ?: throw AssertionError()
                    }
            }
        """

        val findings = NamingBackingProperties(Config.empty).compileAndLint(code)
        assertThat(findings).hasSize(1)
    }
}
