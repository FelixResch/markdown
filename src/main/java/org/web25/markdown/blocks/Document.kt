package org.web25.markdown.blocks

import io.femo.markdown.Tag
import io.femo.markdown.set
import java.util.*

/**
 * Created by felix on 10/17/16.
 */
class Document(incremental: Boolean = true): ContainerBlock(null, incremental) {

    private val linkDefinitions: MutableMap<String, LinkDefinition> = HashMap()

    override fun getBlockTag(): Tag {
        return EmptyTag()
    }

    override fun name(): String = "document"

    override fun nodeType(): String = Document::class.qualifiedName.toString()

    private class EmptyTag: Tag("") {

        override fun toString(): String {
            return (if (children.isEmpty()) "" else children.joinToString(separator = ""))
        }
    }

    fun addLinkDefinition(label: String, destination: String, title: String?) {
        if(linkDefinitions.containsKey(label)) {
            throw MarkdownException("Labels can only be defined once")
        }
        linkDefinitions.put(label, LinkDefinition(label, destination, title))
    }

    fun getLinkDefinition(label: String): LinkDefinition {
        if(linkDefinitions.containsKey(label)) {
            return linkDefinitions[label]!!
        } else {
            throw MarkdownException("Link definition not found")
        }
    }

    fun updateLinkDefinition(label: String, newLabel: String? = null, newDestination: String? = null, newTitle: String? = null) {
        if(!linkDefinitions.containsKey(label)) {
            throw MarkdownException("Link definition not found")
        }
        val def = linkDefinitions[label]!!
        if(newLabel != null) {
            def.label = newLabel
            linkDefinitions.remove(label)
            linkDefinitions.put(newLabel, def)
        }
        if(newDestination != null) {
            def.destination = newDestination
        }
        if(newTitle != null) {
            def.title = newTitle
        }
    }
}

data class LinkDefinition(var label: String, var destination: String, var title: String?)