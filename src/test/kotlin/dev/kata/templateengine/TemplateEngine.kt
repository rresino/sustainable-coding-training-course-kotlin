package dev.kata.templateengine

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TemplateEngine {

    companion object {
        fun foo(template: String?, vars: Map<String, String>?): String {
            var str = template?:""

            vars?.forEach {
                str = str.replace(it.key, it.value)
            }

            return str
        }
    }

}


class TemplateEngineShould {

    /*
        TODO-list: especificacion de requisitos.

        1 - null, null -> ""
        2 - "", [] -> ""
        3 - "hola", [] -> "hola"
        4 - "{$var1}", [] -> "{$var1}"
        5 - "{$var1}", [$var2: hola] - "{$var1}"
        6 - "hola {$placeholder}", [$placeholder: mundo] -> "hola mundo"
        7 - "hola {$a1} {$b2} {$a1}", [$a1: foo, $b2: bar] -> "hola foo bar foo"
        8 - "hola {$a1} {$b2}", [$a1: foo] -> "hola foo {$b2}"

     */

    // null, null -> ""
    @Test
    fun `foo should spec 1 - null, null returns empty string`() {
        assertEquals("", TemplateEngine.foo(null, null))
    }

    // "", [] -> ""
    @Test
    fun `foo should spec 2 - null, empty map returns empty string`() {
        assertEquals("hola", TemplateEngine.foo("hola", mapOf<String, String>()))
    }

    // "hola", [] -> "hola"
    @Test
    fun `foo should spec 3 - hola return hola`() {
        assertEquals( "hola", TemplateEngine.foo("hola", mapOf<String, String>()))

    }

    // "{$var1}", [] -> "{$var1}"
    @Test
    fun `foo should spec 4 - var1 and empty map returns input string`() {
        assertEquals( "{\$var1}", TemplateEngine.foo("{\$var1}", mapOf<String, String>()))
    }

    // "{$var1}", [$var2: hola] -> ???
    @Test
    fun `foo should spec 5 - var1 and map with other items returns input string`() {
        assertEquals( "{\$var1}", TemplateEngine.foo("{\$var1}", mapOf<String, String>()))
    }

    // "hola {$placeholder}", [$placeholder: mundo] -> "hola mundo"
    @Test
    fun `foo should spec 6 - str with vars and map with item returns string with item`() {
        assertEquals( "hola mundo",
            TemplateEngine.foo("hola {\$placeholder}", mapOf("{\$placeholder}" to "mundo")))
    }

    // "hola {$a1} {$b2} {$a1}", [$a1: foo, $b2: bar] -> "hola foo bar foo"
    @Test
    fun `foo should spec 7 - long str many vars + map with many items returns string parsed`() {
        assertEquals("hola foo bar foo",
            TemplateEngine.foo("hola {\$a1} {\$b2} {\$a1}", mapOf("{\$a1}" to "foo", "{\$b2}" to "bar")))
    }

    // "hola {$a1} {$b2}", [$a1: foo] -> "hola foo {$b2}"
    @Test
    fun `foo should spec 8 - long str many vars + map with not all items returns string semi-parsed`() {
        assertEquals("hola foo {\$b2}",
            TemplateEngine.foo("hola {\$a1} {\$b2}", mapOf("{\$a1}" to "foo")))
    }
}