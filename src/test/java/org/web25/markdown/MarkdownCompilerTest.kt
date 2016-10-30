package org.web25.markdown

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

    @Test
    fun testStringInsert() {
        val text = "Hello World"
        val text1 = text.insertAt(5, " lovely")
        assertEquals("Hello lovely World", text1)
    }

    @Test
    fun codePointTest() {
        val codePoint = 0x20;
        val block = Character.UnicodeBlock.of(codePoint);
        println(block);
    }
}