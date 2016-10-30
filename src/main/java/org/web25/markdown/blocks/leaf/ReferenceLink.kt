package org.web25.markdown.blocks.leaf

import io.femo.markdown.Tag
import io.femo.markdown.addAll
import io.femo.markdown.blocks.ContainerBlock
import io.femo.markdown.blocks.LeafBlock
import io.femo.markdown.blocks.MarkdownException
import io.femo.markdown.set
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("Markdown")

/**
 * Created by felix on 10/19/16.
 */
class ReferenceLink(parent: ContainerBlock, var reference: String) : LeafBlock(parent) {

    override fun name(): String = "reference_link"

    override fun nodeType(): String = ReferenceLink::class.qualifiedName!!

    override fun renderTags(): Tag {
        try {
            val definition = getDocument().getLinkDefinition(reference)
            val link = Tag("a")
            link.addAll(inlineTags())
            link.set("href", definition.destination)
            if(definition.title != null) {
                link.set("title", definition.title)
            }
            return link
        } catch (e: MarkdownException) {
            logger.warn(e.message)
            return EmptyTag()
        }

    }
}