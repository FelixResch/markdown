package org.web25.markdown.blocks.leaf

import io.femo.markdown.Tag
import io.femo.markdown.blocks.ContainerBlock
import io.femo.markdown.blocks.LeafBlock
import io.femo.markdown.set

/**
 * Created by felix on 10/17/16.
 */
class ThematicBreak(parent: ContainerBlock): LeafBlock(parent) {

    override fun name(): String = "thematic_break"

    override fun nodeType(): String = ThematicBreak::class.qualifiedName.toString()

    override fun renderTags(): Tag {
        return Tag("hr")
    }
}