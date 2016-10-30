package org.web25.markdown

import java.util.*

/**
 * Created by felix on 10/17/16.
 */
abstract class Node(var parent: Node?, val incremental: Boolean = true) {

    abstract fun name(): String
    abstract fun nodeType(): String
    protected abstract fun renderTags(): Tag

    protected val id: UUID

    init {
        this.id = UUID.randomUUID()
    }

    fun render(): Tag {
        val tag = renderTags()
        if(incremental)
            tag.set("id", id.toString())
        return tag
    }

    fun hasParent(): Boolean {
        return parent != null;
    }
}