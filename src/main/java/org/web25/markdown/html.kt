package org.web25.markdown

/*
From kotlin koans + extensions
 */
import java.util.ArrayList

open class Tag(val name: String) {
    val children: MutableList<Tag> = ArrayList()
    val attributes: MutableList<Attribute> = ArrayList()
    var full: Boolean = false

    override fun toString(): String {
        return "<$name" +
                (if (attributes.isEmpty()) "" else attributes.joinToString(separator = " ", prefix = " ")) +
                (if (children.isEmpty() && !full) "" else ">") +
                (if (children.isEmpty()) "" else children.joinToString(separator = "")) +
                (if (children.isEmpty() && !full) " />" else "</$name>")
    }

    fun add(tag: Tag): Tag {
        children.add(tag)
        return this
    }
}

class Attribute(val name : String, val value : String) {
    override fun toString() = """$name="$value""""
}

fun <T: Tag> T.set(name: String, value: String?): T {
    if (value != null) {
        attributes.add(Attribute(name, value))
    }
    return this
}

fun <T: Tag> Tag.doInit(tag: T, init: T.() -> Unit): T {
    tag.init()
    children.add(tag)
    return tag
}

fun <T: Tag> T.full(): T {
    this.full = true
    return this
}

fun <T: Tag> T.addAll(tags: Set<Tag>): T {
    this.children.addAll(tags)
    return this
}

class Html: Tag("html")
class Table: Tag("table")
class Center: Tag("center")
class TR: Tag("tr")
class TD: Tag("td")
class Text(val text: String): Tag("b") {
    override fun toString() = text
}

fun html(init: Html.() -> Unit): Html = Html().apply(init)

fun Html.table(init : Table.() -> Unit) = doInit(Table(), init)
fun Html.center(init : Center.() -> Unit) = doInit(Center(), init)

fun Table.tr(color: String? = null, init : TR.() -> Unit) = doInit(TR(), init).set("bgcolor", color)

fun TR.td(color: String? = null, align : String = "left", init : TD.() -> Unit) = doInit(TD(), init).set("align", align).set("bgcolor", color)

fun Tag.text(s : Any?) = doInit(Text(s.toString()), {})