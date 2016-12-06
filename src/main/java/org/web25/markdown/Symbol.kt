package org.web25.markdown

/**
 * Created by felix on 12/6/16.
 */
data class Symbol(val printableCharacter: Int, val escaped: Boolean, val characters: List<Int>, val position: Int)