package org.web25.markdown

import org.web25.codePointIterator
import java.util.*

/**
 * Created by felix on 12/6/16.
 */
class Symbolizer {

    fun symbolize(document: String): List<Symbol> {
        var escaped = false
        var position = 0
        val symbols = ArrayList<Symbol>()
        document.codePointIterator {
            if(escaped) {
                symbols.add(Symbol(it, true, listOf('\\'.toInt(), it), position))
                position++
                escaped = false
            } else {
                if(it == '\\'.toInt()) {
                    escaped = true
                } else {
                    val char = if(it == 0) 0xfffd else it
                    symbols.add(Symbol(char, false, listOf(it), position))
                    position++
                }
            }
        }
        return symbols
    }
}