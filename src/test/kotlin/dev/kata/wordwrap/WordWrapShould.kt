package dev.kata.wordwrap

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class WordWrap {

    companion object {

        fun wordwrap(fullText: String, maxCols: Int): String {
            if (fullText.length <= maxCols) {
                return fullText.trim()
            }

            var tentativeStr = fullText.substring(0, maxCols)

            if (tentativeStr.contains(" ")) {
                val lastPositionSpace = tentativeStr.indexOfLast { it == ' '}
                tentativeStr = tentativeStr.substring(0, lastPositionSpace)
            }

            return tentativeStr.trim() + "\n" + wordwrap(fullText.substring(tentativeStr.length).trim(), maxCols)
        }
    }

}


class WordWrapShould {

    // casos limite
    // "hola", 2 -> "ho\nla"
    // "hola mundo", 8   -> "hola\nmundo"
    // "abracadabra", 2 -> "ab\nra\ncad\nab\nra"
    // "hola abracadabra", 3 -> "hol\na\nabr\naca\ndab\nra"
    // "hola    abracadabra", 3 -> "hol\na\nabr\naca\ndab\nra"

    @Test
    fun `Split string with no spaces by maxcols chars `() {
        val rs = WordWrap.wordwrap(fullText = "hola", maxCols = 2)
        assertThat(rs).isEqualTo("ho\nla")
    }

    @Test
    fun `If string to split has spaces inside world to split, split on space and not in maxcols`() {
        val rs = WordWrap.wordwrap(fullText = "hola mundo", maxCols = 8)
        assertThat(rs).isEqualTo("hola\nmundo")
    }

    @Test
    fun `For a long string with no space, it will split word by maxcols, until no more chars`() {
        val rs = WordWrap.wordwrap(fullText = "abracadabra", maxCols = 2)
        assertThat(rs).isEqualTo("ab\nra\nca\nda\nbr\na")
    }


    @Test
    fun `If string to split has multiple spaces inside world to split, split on last space and not in maxcols`() {
        val rs = WordWrap.wordwrap(fullText = "ab r acadabra", maxCols = 5)
        assertThat(rs).isEqualTo("ab r\nacada\nbra")
    }

    @Test
    fun `If string has multiples spaces on end, split by space and remove redundant spaces`() {
        val rs = WordWrap.wordwrap(fullText = "hola    abracadabra", maxCols = 3)
        assertThat(rs).isEqualTo("hol\na\nabr\naca\ndab\nra")
    }

//    @Test
//    fun `If maxcols are 0 it will return and empty string`() {
//        val rs = WordWrap.wordwrap(fullText = "hola    abracadabra", maxCols = 0)
//        assertThat(rs).isEqualTo("")
//    }

//    @Test
//    fun `If maxcols are less of 0, it will return and empty string`() {
//        val rs = WordWrap.wordwrap(fullText = "hola    abracadabra", maxCols = -1)
//        assertThat(rs).isEqualTo("")
//    }

}
