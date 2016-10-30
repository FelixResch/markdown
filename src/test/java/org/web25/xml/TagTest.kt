package org.web25.xml

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by felix on 10/28/16.
 */
class TagTest {

    @Test
    fun testXML() {
        assertEquals("""<test>
    <version type="old">1.0</version>
</test>""", Tag("test", Tag("version", Text("1.0")).set("type", "old")).render())
    }

    @Test
    fun testAST() {
        assertEquals("""<document web25:version="0.26" web25:specification="commonmark">
    <thematic_break />
    <thematic_break />
    <thematic_break />
</document>""",
                Tag("document",
                        Tag("thematic_break"),
                        Tag("thematic_break"),
                        Tag("thematic_break")
                )
                .set("web25:specification", "commonmark")
                .set("web25:version", "0.26").render()
        )
    }
}