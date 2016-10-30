package org.web25.markdown.blocks

import io.femo.markdown.Node
import io.femo.markdown.Tag
import io.femo.markdown.inlines.Inline
import io.femo.markdown.set
import java.io.PrintStream
import java.util.*

/**
 * Created by felix on 10/17/16.
 */
abstract class Block (parent: ContainerBlock?, incremental: Boolean = (if (parent != null) parent.incremental else true)) : Node(parent, incremental){

    private val inlines: MutableList<Inline>

    init {
        this.inlines = ArrayList()
    }

    fun add(inline: Inline) {
        this.inlines.add(inline)
    }

    fun remove(inline: Inline) {
        this.inlines.remove(inline)
    }

    fun removeInlineAt(index: Int) {
        this.inlines.removeAt(index)
    }

    fun indexOf(inline: Inline): Int {
        return this.inlines.indexOf(inline)
    }

    fun insertAt(index: Int, inlines: Array<out Inline>) {
        this.inlines.addAll(index, inlines.asList())
    }

    fun replaceWith(vararg blocks: Block) {
        if(hasParent() && this.parent is ContainerBlock) {
            val parent: ContainerBlock = this.parent as ContainerBlock;
            val pos = parent.indexOf(this)
            parent.removeAt(pos)
            parent.insertAt(pos, blocks)
        }
    }

    fun inlineTags(): Set<Tag> {
        return inlines.map(Inline::render).toSet()
    }

    fun getDocument(): Document {
        if(this is Document) {
            return this
        } else if (parent!= null && parent is Document) {
            return parent as Document
        } else if (parent != null && parent is Block) {
            return (parent as Block).getDocument()
        } else {
            throw MarkdownException("No document for Node found")
        }
    }

    fun printAST(println : (String) -> Any) {

    }

}

class MarkdownException(s: String) : RuntimeException(s)





