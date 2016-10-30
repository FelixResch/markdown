package org.web25.xml

import org.web25.Renderable

/**
 * Created by felix on 10/28/16.
 */
class Text(val text: String) : Renderable {
    override fun isLeaf(): Boolean {
        return true
    }

    override fun render(): String {
        return text
    }
}