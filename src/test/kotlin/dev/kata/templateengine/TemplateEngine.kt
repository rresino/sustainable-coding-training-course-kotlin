package dev.kata.templateengine

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TemplateEngine {

    companion object {
        fun foo(template: String?, vars: Map<String, String>?): String {
            return template?:""
        }
    }

}


class TemplateEngineShould {

    /*
        TODO-list: especificacion de requisitos.

        1 - null, null -> ""
        2 - "", [] -> ???
        3 - "hola", [] -> "hola"
        4 - "{$var1}", [] -> ???
        5 - "{$var1}", [$var2: hola] -> ???
        6 - "hola {$placeholder}", [$placeholder: mundo] -> "hola mundo"
        7 - "hola {$a1} {$b2} {$a1}", [$a1: foo, $b2: bar] -> "hola foo bar foo"
        8 - "hola {$a1} {$b2}", [$a1: foo] -> ???

     */
    @Test
    fun `foo should spec 3 - hola return hola`() {

        val rs = TemplateEngine.foo("hola", mapOf<String, String>())

        assertEquals(rs, "hola")

    }

    @Test
    fun `foo should spec 1 - null, null returns empty string`() {
        val rs = TemplateEngine.foo(null, null)

        assertEquals(rs, "")

    }
}