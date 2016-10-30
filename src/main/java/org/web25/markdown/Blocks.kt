package org.web25.markdown

import java.util.*

/**
 * Created by felix on 10/3/16.
 */

class BlockMatcher {

    var beginMatcher: ((org.web25.markdown.SymbolGroup) -> Boolean)? = null
    var continuingMatcher: ((org.web25.markdown.SymbolGroup) -> Boolean)? = null
    var closeMatcher: ((org.web25.markdown.SymbolGroup) -> Boolean)? = null

    fun begin(matcher: (org.web25.markdown.SymbolGroup) -> Boolean): org.web25.markdown.BlockMatcher {
        beginMatcher = matcher
        return this
    }

    fun continuing(matcher: (org.web25.markdown.SymbolGroup) -> Boolean): org.web25.markdown.BlockMatcher {
        continuingMatcher = matcher
        return this
    }

    fun close(matcher: (org.web25.markdown.SymbolGroup) -> Boolean): org.web25.markdown.BlockMatcher {
        closeMatcher = matcher
        return this
    }

}

fun not (matcher: (org.web25.markdown.SymbolGroup) -> Boolean): (org.web25.markdown.SymbolGroup) -> Boolean {
    return {!matcher(it)}
}

fun and(vararg matcher: (org.web25.markdown.SymbolGroup) -> Boolean): (org.web25.markdown.SymbolGroup) -> Boolean {
    return {
        matcher.all { m -> m.invoke(it) }
    }
}

fun or(vararg matcher: (SymbolGroup) -> Boolean): (SymbolGroup) -> Boolean {
    return {
        matcher.any { m -> m.invoke(it) }
    }
}

fun beginsWith(codePoint: Int, count: Int = 1, maxSpaces: Int = 0): (SymbolGroup) -> Boolean {
    return {
        var res = true
        for (i: Int in 0..maxSpaces) {
            if(!it.symbols[i].classes.any { it is WhitespaceCharacterClass || it is UnicodeWhitespaceCharacterClass }) {
                res = false
            }
        }
        if(res)
            for (i: Int in 0..(count - 1)) {
                if(it.symbols[i].codePoint != codePoint)
                    res = false
            }
        res
    }
}

fun beginsWithClass(symbolClass: SymbolClass, count: Int = 1): (SymbolGroup) -> Boolean {
    return {
        var res = true
        for(i: Int in 0..(count - 1)) {
            if(!it.symbols[i].classes.any { it == symbolClass })
                res = false
        }
        res
    }
}

fun isBlank(): (SymbolGroup) -> Boolean {
    return {
        if(it is Line) {
            it.isBlank()
        } else {
            false
        }
    }
}

fun toCodepoint(char: Char): Int {
    return Character.toCodePoint(0.toChar(), char)
}

abstract class BlockClass(val name: String) {

    abstract fun render(block: Block): Tag

    protected abstract fun matcher(): BlockMatcher

}

class Block (val from: Int, val to: Int, val blockClass: BlockClass, val symbols: MutableList<Symbol>)

class BlockQuoteClass() : BlockClass("block quotes") {

    override fun matcher(): BlockMatcher {
        return BlockMatcher()
                .begin(beginsWith(toCodepoint('>'), 1, 3))
                .continuing(
                        or(
                                beginsWith(toCodepoint('>'), 1, 3),
                                not(
                                        or(
                                                beginsWith(toCodepoint(' '), 4),
                                                beginsWith(toCodepoint('-'), 3, 3),
                                                beginsWith(toCodepoint('*'), 1, 3)
                                        )
                                )
                        )
                )
                .close(isBlank())
    }

    override fun render(block: Block): Tag {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
