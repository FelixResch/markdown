package org.web25.markdown.blocks

import io.femo.markdown.Tag
import java.util.*

/**
 * Created by felix on 10/17/16.
 */
abstract class ContainerBlock(parent: ContainerBlock?, incremental: Boolean = (if (parent != null) parent.incremental else true)): Block(parent, incremental) {

    private val children: MutableList<Block>

    init {
        this.children = ArrayList()
    }

    fun add(block: Block) {
        this.children.add(block)
    }

    fun remove(block: Block) {
        this.children.remove(block)
    }

    fun removeAt(index: Int) {
        this.children.removeAt(index)
    }

    fun indexOf(block: Block): Int {
        return this.children.indexOf(block)
    }

    fun insertAt(index: Int, blocks: Array<out Block>) {
        this.children.addAll(index, blocks.asList())
    }

    override fun renderTags(): Tag {
        val tag = this.getBlockTag();
        this.children.forEach {
            tag.children.add(it.render())
        }
        return tag
    }

    protected abstract fun getBlockTag(): Tag

}