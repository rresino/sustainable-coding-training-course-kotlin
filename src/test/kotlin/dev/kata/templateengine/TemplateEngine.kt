package dev.kata.templateengine

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TemplateEngine {

    companion object {
        fun foo(template: String, vars: Map<String, String>): String {
            return template
        }
    }

}


class TemplateEngineShould {

    /*
        TODO-list: especificacion de requisitos.

        null, null -> ???
        "", [] -> ???
        "hola", [] -> "hola"
        "{$var1}", [] -> ???
        "{$var1}", [$var2: hola] -> ???
        "hola {$placeholder}", [$placeholder: mundo] -> "hola mundo"
        "hola {$a1} {$b2} {$a1}", [$a1: foo, $b2: bar] -> "hola foo bar foo"
        "hola {$a1} {$b2}", [$a1: foo] -> ???

     */
    @Test
    fun foo() {

        val rs = TemplateEngine.foo("hola", mapOf<String, String>())

        assertEquals(rs, "hola")

    }
}