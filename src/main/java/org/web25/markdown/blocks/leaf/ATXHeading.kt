package org.web25.markdown.blocks.leaf

import io.femo.markdown.Tag
import io.femo.markdown.addAll
import io.femo.markdown.blocks.ContainerBlock
import io.femo.markdown.blocks.LeafBlock

/**
 * Created by felix on 10/17/16.
 */
class ATXHeading(parent: ContainerBlock, val level: Int) : LeafBlock(parent) {

    override fun name(): String = "atx_heading"

    override fun nodeType(): String = ATXHeading::class.qualifiedName!!

    override fun renderTags(): Tag {
        return Tag("h" + level).addAll(inlineTags())
    }

}
