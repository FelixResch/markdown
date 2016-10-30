package org.web25

/**
 * Created by felix on 10/28/16.
 */
interface Renderable {

    fun render(): String
    fun isLeaf(): Boolean
}