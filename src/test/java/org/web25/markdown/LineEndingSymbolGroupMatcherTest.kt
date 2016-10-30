package org.web25.markdown

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by felix on 10/1/16.
 */
class LineEndingSymbolGroupMatcherTest {

    @Test
    fun testMultiLineCorrectRange() {
        val document = MarkdownCompiler().compile("Hello World\nIs this awesome\n?\n\n")
        val matcher = LineEndingSymbolGroupMatcher()
        val symbolGroup = matcher.match(document.symbols, 0)
        assertEquals(11, symbolGroup.from);
        assertEquals(11, symbolGroup.to);
        val symbolGroup2 = matcher.match(document.symbols, 12)
        assertEquals(27, symbolGroup2.from);
        assertEquals(27, symbolGroup2.to);
    }
}