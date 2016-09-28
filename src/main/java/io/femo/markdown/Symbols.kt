package io.femo.markdown

/**
 * Class representing a unicode character of the document, and its linkage to characters inside the input string and the AST
 *
 * @author Felix Resch
 * @since 0.0.1
 * @property codePoint the codePoint the Symbol is wrapping
 */
data class Symbol (val codePoint: Int, val position: Long, var before: Symbol?, var next: Symbol?)


/**
 * Class that can be used to symbols into one class
 */
abstract class SymbolClass(val name: String) {

    abstract fun matches(symbol: Symbol): Boolean

}