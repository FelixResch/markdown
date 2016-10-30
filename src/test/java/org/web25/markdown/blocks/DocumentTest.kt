package org.web25.markdown.blocks

import io.femo.markdown.blocks.leaf.*
import io.femo.markdown.inlines.Text
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by felix on 10/17/16.
 */
class DocumentTest {

    @Test
    fun testThematicBreakSimple() {
        val doc = Document(false)
        doc.add(ThematicBreak(doc))
        assertEquals("<hr />", doc.render().toString())
    }

    @Test
    fun testThematicBreakIncremental() {
        val doc = Document()
        doc.add(ThematicBreak(doc))
        val html = doc.render().toString()
        assertTrue("Begins with <hr id=\"", html.startsWith("<hr id=\""))
        assertTrue("Ends with \" />", html.endsWith("\" />"))
    }

    @Test
    fun testATXHeadingSimple() {
        val doc = Document(false)
        val heading = ATXHeading(doc, 1)
        heading.add(Text(heading, "Hello World"))
        doc.add(heading)
        assertEquals("<h1>Hello World</h1>", doc.render().toString())
    }

    @Test
    fun testATXHeadingIncremental() {
        val doc = Document()
        val heading = ATXHeading(doc, 1)
        heading.add(Text(heading, "Hello World"))
        doc.add(heading)
        val html = doc.render().toString()
        assertTrue("Begins with <h1 id=\"", html.startsWith("<h1 id=\""))
        assertTrue("Ends with \">Hello World</h1>", html.endsWith("\">Hello World</h1>"))
    }

    @Test
    fun testCodeBlockSimple() {
        val doc = Document(false)
        val code = Code(doc, "println('Hello World')")
        doc.add(code)
        val html = doc.render().toString()
        assertEquals("<pre><code>println('Hello World')</code></pre>", html)
    }

    @Test
    fun testCodeBlockWithLanguageSimple() {
        val doc = Document(false)
        val code = Code(doc, "println('Hello World')", "javascript")
        doc.add(code)
        val html = doc.render().toString()
        assertEquals("<pre><code class=\"language-javascript\">println('Hello World')</code></pre>", html)
    }

    @Test
    fun testLinkReferenceDefinition() {
        val doc = Document(false)
        val def = LinkReferenceDefinition(doc, "foo", "http://example.com", "example page")
        doc.add(def)
        val def1 = doc.getLinkDefinition("foo")
        assertEquals("foo", def1.label)
        assertEquals("http://example.com", def1.destination)
        assertEquals("example page", def1.title)
        assertEquals("", doc.render().toString())
    }

    @Test
    fun testReferenceLink() {
        val doc = Document(false)
        val def = LinkReferenceDefinition(doc, "foo", "http://example.com", "example page")
        doc.add(def)
        val refLink = ReferenceLink(doc, "foo")
        refLink.add(Text(refLink, "Bar"))
        doc.add(refLink)
        assertEquals("""<a href="http://example.com" title="example page">Bar</a>""", doc.render().toString())
        def.updateTitle("an example page")
        assertEquals("""<a href="http://example.com" title="an example page">Bar</a>""", doc.render().toString())
    }
}