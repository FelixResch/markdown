package org.web25.markdown

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by felix on 12/6/16.
 */
class CompilerTest {

    @Test
    internal fun testInitializationComplete() {
        val compiler = Compiler.createDefault()
        assertNotNull("Tokenizer", compiler.tokenizer)
        assertNotNull("Symbolizer", compiler.symbolizer)
    }
}