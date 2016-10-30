package org.web25.markdown

import java.util.*

/**
 * Class representing a unicode character of the document, and its linkage to characters inside the input string and the AST
 *
 * @author Felix Resch
 * @since 0.0.1
 * @property codePoint the codePoint the Symbol is wrapping
 */
data class Symbol (val codePoint: Int, val position: Int, var before: Symbol?, var next: Symbol? = null, val classes: MutableList<SymbolClass> = ArrayList()) {

    override fun toString(): String {
        return "Symbol(codePoint=$codePoint, position=$position, before=" +
                if(before != null) {before!!.codePoint} else {"null"} +
                ", next=" + if(next != null) {next!!.codePoint} else {"null"} + ", classes=$classes)"
    }

    override fun hashCode(): Int {
        return codePoint;
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Symbol) return false

        if (codePoint != other.codePoint) return false
        if (position != other.position) return false

        return true
    }
}


/**
 * Class that can be used to symbols into one class
 */
abstract class SymbolClass(val name: String) {

    abstract fun matches(symbol: Symbol): Boolean

    override fun toString(): String {
        return "SymbolClass(name='${name.toUpperCase().replace(" ", "_")}')"
    }


}

class CharacterClass(): SymbolClass("character") {
    override fun matches(symbol: Symbol): Boolean = true
}

class NonWhitespaceCharacterClass(): SymbolClass("non-whitespace character") {
    override fun matches(symbol: Symbol): Boolean {
        return !(
                    symbol.codePoint.equalsAny(0x20, 0x09, 0x0a, 0x0b, 0x0c, 0x0d) or
                    (
                            Character.getType(symbol.codePoint).equalsAny(Character.LINE_SEPARATOR, Character.PARAGRAPH_SEPARATOR, Character.SPACE_SEPARATOR) or
                            symbol.codePoint.equalsAny(0x09, 0x0d, 0x0a, 0x0c)
                    )
                )
    }
}

class SpaceCharacterClass(): SymbolClass("space") {
    override fun matches(symbol: Symbol): Boolean = symbol.codePoint == 0x20
}

class TabCharacterClass(): SymbolClass("tab") {
    override fun matches(symbol: Symbol): Boolean = symbol.codePoint == 0x09
}

class LineEndingCharacter(): SymbolClass("line ending") {
    override fun matches(symbol: Symbol): Boolean = symbol.codePoint.equalsAny(0x0a, 0x0d)
}

class WhitespaceCharacterClass(): SymbolClass("whitespace character") {
    override fun matches(symbol: Symbol): Boolean = symbol.codePoint.equalsAny(0x20, 0x09, 0x0a, 0x0b, 0x0c, 0x0d)
}

class UnicodeWhitespaceCharacterClass(): SymbolClass("unicode whitespace character") {
    override fun matches(symbol: Symbol): Boolean = Character.getType(symbol.codePoint).equalsAny(Character.LINE_SEPARATOR, Character.PARAGRAPH_SEPARATOR, Character.SPACE_SEPARATOR) or symbol.codePoint.equalsAny(0x09, 0x0d, 0x0a, 0x0c)
}

open class ASCIIPunctuationCharacterClass(name : String = "ASCII punctuation character"): SymbolClass(name) {
    override fun matches(symbol: Symbol): Boolean = symbol.codePoint.equalsAny('!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~')
}

class PunctuationCharacterClass(): ASCIIPunctuationCharacterClass("punctuation character") {
    override fun matches(symbol: Symbol): Boolean {
        return super.matches(symbol) || Character.getType(symbol.codePoint)
                .equalsAny(
                        Character.CONNECTOR_PUNCTUATION,
                        Character.DASH_PUNCTUATION,
                        Character.END_PUNCTUATION,
                        Character.FINAL_QUOTE_PUNCTUATION,
                        Character.INITIAL_QUOTE_PUNCTUATION,
                        Character.OTHER_PUNCTUATION,
                        Character.START_PUNCTUATION)
    }
}

fun Int.equalsAny(vararg values: Int): Boolean {
    for (value in values) {
        if(value == this)
            return true;
    }
    return false;
}

fun Int.equalsAny(vararg values: Byte): Boolean {
    for (value in values) {
        if(value.toInt() == this)
            return true;
    }
    return false;
}

fun Int.equalsAny(vararg values: Char): Boolean {
    for (value in values) {
        if(value.toInt() == this)
            return true;
    }
    return false;
}