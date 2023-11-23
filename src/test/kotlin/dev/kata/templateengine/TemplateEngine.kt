package dev.kata.templateengine

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

data class TemplateString(val parsedString : String, val missingVars: List<String> = listOf())

class TemplateEngine {

    companion object {
        val variableRegex = "\\{\\\$\\w+\\}"

        fun foo(template: String?, vars: Map<String, String>?): TemplateString {
            var str = template?:""

            vars?.forEach {
                str = str.replace("{\$${it.key}}", it.value)
            }

            val foundList = Regex(variableRegex).findAll(str).toSet().map { it.value }

            foundList.forEach {
                str = str.replace(it,"")
            }

            val cleanfoundList = foundList.map {it.substring(2, it.length-1) }

            return TemplateString(str, cleanfoundList)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val str = "hola {\$a1} {\$b2} {\$a1}"

            val r = "\\{\\\$\\w+\\}"

            val rs = Regex(r).findAll(str,0).toList()

            println(rs.map { it.value })
            println(rs.size)

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
    fun `No string and no variable returns empty string`() {
        val rs = TemplateEngine.foo(null, null)
        assertEquals(
            TemplateString("", listOf()),
            rs)
        assert(rs.missingVars.isEmpty())
    }

    // "", [] -> ""
    @Test
    fun `No string and empty variables returns empty string`() {
        val rs = TemplateEngine.foo("hola", mapOf())
        assertEquals("hola", rs.parsedString)
        assertEquals(0, rs.missingVars.size)
    }

    // "hola", [] -> "hola"
    @Test
    fun `if string are without vars it will return same string`() {
        val rs = TemplateEngine.foo("hola", mapOf())
        assertEquals( "hola", rs.parsedString)
        assert(rs.missingVars.isEmpty())
    }

    // "{$var1}", [] -> ""
    @Test
    fun `if string has vars and it will not in items, it will replace var for empty string and add it in missing vars`() {

        val rs =  TemplateEngine.foo("{\$var1}", mapOf())
        assertEquals( "", rs.parsedString)
        assertEquals(1, rs.missingVars.size)
        assertEquals(listOf("var1"), rs.missingVars)
    }

    // "{$var1}", [$var2: hola] -> ""
    @Test
    fun `if string has variable and not in variables, string will clean not found variable and add missing vars as warning`() {
        val rs = TemplateEngine.foo("{\$var1}", mapOf())
        assertEquals( "", rs.parsedString)
        assertEquals( 1, rs.missingVars.size )
        assertEquals(listOf("var1"), rs.missingVars)
    }

    // "hola {$placeholder}", [$placeholder: mundo] -> "hola mundo"
    @Test
    fun `a var in string will be replace for variable values`() {
        val rs = TemplateEngine.foo("hola {\$placeholder}", mapOf("placeholder" to "mundo"))
        assertEquals("hola mundo", rs.parsedString)
        assert(rs.missingVars.isEmpty())
    }

    // "hola {$a1} {$b2} {$a1}", [$a1: foo, $b2: bar] -> "hola foo bar foo"
    @Test
    fun `the string will replace for all ocurrences, one o many found will be replaces`() {
        val rs = TemplateEngine.foo("hola {\$a1} {\$b2} {\$a1}", mapOf("a1" to "foo", "b2" to "bar"))

        assertEquals("hola foo bar foo", rs.parsedString)
        assert(rs.missingVars.isEmpty())
    }

    // "hola {$a1} {$b2}", [$a1: foo] -> "hola foo "
    @Test
    fun `A string with many vars, will replaces all for found variable values and empty string for not found, all the missing found will be add to warning`() {
        val rs = TemplateEngine.foo("hola {\$a1} {\$b2}", mapOf("a1" to "foo"))

        assertEquals("hola foo ", rs.parsedString)
        assertEquals(1, rs.missingVars.size)
        assertEquals(listOf("b2"), rs.missingVars)

    }
}