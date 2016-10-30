package org.web25.markdown.inlines

import io.femo.markdown.Node
import io.femo.markdown.Tag
import io.femo.markdown.blocks.Block

/**
 * Created by felix on 10/17/16.
 */
class Text(parent: Node?, val text: String) : Inline(parent) {
    override fun name(): String = "text"

    override fun nodeType(): String = Text::class.qualifiedName!!

    override fun renderTags(): Tag {
        return io.femo.markdown.Text(text)
    }

}