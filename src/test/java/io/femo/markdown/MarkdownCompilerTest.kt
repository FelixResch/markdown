package io.femo.markdown

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by Felix Resch on 29-Aug-16.
 */
class MarkdownCompilerTest {


    @Test
    fun testDoument() {
        val compiler = MarkdownCompiler();
        val document = compiler.compile("Hello World")
        assertEquals(compiler, document.compiler)
        assertEquals("Hello World", document.markdown)
        document.markdown = "Hi World";
        assertNotEquals("Hi World", document.markdown)
    }
}