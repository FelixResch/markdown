package org.web25

/**
 * Created by felix on 10/28/16.
 */

fun String.indentLines(count: Int, char: Char = ' '): String {
    return this.split("\n").joinToString("\n" + String.of(char, count))
}

fun String.Companion.of(char: Char, count: Int): String {
    var result = ""
    for (i in 1..count) {
        result += char
    }
    return result
}