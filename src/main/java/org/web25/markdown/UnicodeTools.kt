package org.web25.markdown

import java.util.*
import java.util.function.Consumer

/**
 * Created by felix on 9/28/16.
 */

fun String.codePointIterator(consumer: (Int) -> Unit) {
    var i: Int = 0
    val length: Int = this.length
    while (i < length) {
        val codePoint = this.codePointAt(i)
        consumer(codePoint)
        i += 2 - this.codePointCount(i, i + 1);
    }
}

fun String.codePoints(): IntArray {
    val array : MutableList<Int> = ArrayList<Int>()
    this.codePointIterator {
        array.add(it)
    }
    return array.toIntArray();
}