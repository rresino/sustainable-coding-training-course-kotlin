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
        val rs = TemplateEngine.foo(null, null)

        assertEquals("", rs)

    }

    // "", [] -> ""
    @Test
    fun `foo should spec 2 - null, empty map returns empty string`() {
        val rs = TemplateEngine.foo("hola", mapOf<String, String>())

        assertEquals("hola", rs)
    }

    // "hola", [] -> "hola"
    @Test
    fun `foo should spec 3 - hola return hola`() {

        val rs = TemplateEngine.foo("hola", mapOf<String, String>())

        assertEquals( "hola", rs)

    }

    // "{$var1}", [] -> "{$var1}"
    @Test
    fun `foo should spec 4 - var1 and empty map returns input string`() {

        val rs = TemplateEngine.foo("{\$var1}", mapOf<String, String>())

        assertEquals( "{\$var1}", rs)
    }

    // "{$var1}", [$var2: hola] -> ???
    @Test
    fun `foo should spec 5 - var1 and map with other items returns input string`() {

        val rs = TemplateEngine.foo("{\$var1}", mapOf<String, String>())

        assertEquals( "{\$var1}", rs)
    }

    // "hola {$placeholder}", [$placeholder: mundo] -> "hola mundo"
    @Test
    fun `foo should spec 6 - str with vars and map with item returns string with item`() {

        val rs = TemplateEngine.foo("hola {\$placeholder}", mapOf("{\$placeholder}" to "mundo"))
        assertEquals( "hola mundo", rs)

    }

    // "hola {$a1} {$b2} {$a1}", [$a1: foo, $b2: bar] -> "hola foo bar foo"
    @Test
    fun `foo should spec 7 - long str many vars + map with many items returns string parsed`() {

        val rs = TemplateEngine.foo("hola {\$a1} {\$b2} {\$a1}", mapOf("{\$a1}" to "foo", "{\$b2}" to "bar"))
        assertEquals("hola foo bar foo", rs)
    }

    // "hola {$a1} {$b2}", [$a1: foo] -> "hola foo {$b2}"
    @Test
    fun `foo should spec 8 - long str many vars + map with not all items returns string semi-parsed`() {

        val rs = TemplateEngine.foo("hola {\$a1} {\$b2}", mapOf("{\$a1}" to "foo"))
        assertEquals("hola foo {\$b2}", rs)
    }
}