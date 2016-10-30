package org.web25.xml

import org.web25.Renderable
import java.util.*

/**
 * Created by felix on 10/28/16.
 */
class Tag (val name: String, vararg children: Renderable): Renderable {

    val children : MutableList<Renderable> = ArrayList()
    val attributes : MutableMap<String, Attribute> = HashMap()

    data class Attribute(val name: String, var value: String): Renderable {
        override fun isLeaf(): Boolean {
            return true
        }

        override fun render(): String {
            return """$name="$value""""
        }
    }

    init {
        this.children.addAll(children.toList())
    }

    fun set(name: String, value: String): Tag {
        if(attributes.containsKey(name)) {
            attributes[name]?.value = value
        } else {
            attributes.put(name, Attribute(name, value))
        }
        return this
    }

    fun get(name: String): String? {
        return attributes[name]?.value
    }

    fun add(tag: Renderable): Tag {
        this.children.add(tag)
        return this
    }

    override fun render(): String {
        return "<$name" +
            if(attributes.count() > 0) {
                " " + attributes.values.map { it.render() }.joinToString(separator = " ")
            } else {
                ""
            } +
            if(children.count() > 0) {
                ">" +
                if(children.count() == 1 && children[0].isLeaf()) {
                    children[0].render()
                } else {
                    children.map { it.render() }.joinToString(separator = "\n", prefix = "\n").indentLines(4) + "\n"
                } +
                "</$name>"
            } else {
                " />"
            }
    }

    override fun isLeaf(): Boolean {
        return false
    }
}
