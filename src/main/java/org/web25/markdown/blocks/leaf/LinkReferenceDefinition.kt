package org.web25.markdown.blocks.leaf

import io.femo.markdown.Tag
import io.femo.markdown.blocks.ContainerBlock
import io.femo.markdown.blocks.Document
import io.femo.markdown.blocks.LeafBlock

/**
 * Created by felix on 10/19/16.
 */
class LinkReferenceDefinition(parent: ContainerBlock, var label: String, var destination: String, var title: String?) : LeafBlock(parent) {

    init {
        val doc = getDocument()
        doc.addLinkDefinition(label, destination, title)
    }

    fun updateLabel(newLabel: String) {
        val doc = getDocument()
        doc.updateLinkDefinition(label, newLabel = newLabel)
        label = newLabel
    }

    fun updateDestination(newDestination: String) {
        val doc = getDocument()
        doc.updateLinkDefinition(label, newDestination = newDestination)
        destination = newDestination
    }

    fun updateTitle(newTitle: String) {
        val doc = getDocument()
        doc.updateLinkDefinition(label, newTitle = newTitle)
        title = newTitle
    }

    override fun name(): String = "link_reference_definition"

    override fun nodeType(): String = LinkReferenceDefinition::class.qualifiedName!!

    override fun renderTags(): Tag {
        return EmptyTag()
    }

}

class EmptyTag : Tag("") {
    override fun toString(): String = ""
}
