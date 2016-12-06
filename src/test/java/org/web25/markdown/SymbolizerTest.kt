package org.web25.markdown

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by felix on 12/6/16.
 */
class SymbolizerTest {
    @Test
    fun testUnescapedText() {
        val symbolizer = Symbolizer()
        val symbols = symbolizer.symbolize("This is a plain text")
        assertTrue("No escaped characters", symbols.all { it.escaped == false })
    }

    @Test
    fun testEscapedText() {
        val symbolizer = Symbolizer()
        val symbols = symbolizer.symbolize("This is not a plain text \\*")
        assertFalse("No all escaped characters", symbols.all { it.escaped == true })
        val last = symbols.last()
        assertEquals("Asterisk", '*'.toInt(), last.printableCharacter)
        assertTrue("Escaped", last.escaped)
        assertTrue("Characters match", listOf('\\'.toInt(), '*'.toInt()) == last.characters)
    }

    @Test
    fun testIllegalCharacter() {
        val symbolizer = Symbolizer()
        val symbols = symbolizer.symbolize("\u0000")
        val first = symbols.first()
        assertNotNull(first)
        assertEquals("Replaced", 0xfffd, first.printableCharacter)
    }
}