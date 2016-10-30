package org.web25.markdown

/**
 * Created by felix on 10/1/16.
 */
open class SymbolGroup(val from: Int, val to: Int, val name: String, val symbols: List<Symbol>)

class Line(from: Int, to: Int, name: String, symbols: List<Symbol>) : SymbolGroup(from, to, name, symbols) {

    fun isBlank(): Boolean {
        return symbols.isEmpty() || symbols.all { it.classes.any { it.javaClass == SpaceCharacterClass::class.java || it.javaClass == TabCharacterClass::class.java } }
    }
}

class NullSymbolGroup(): SymbolGroup(-1, -1, "NULL", listOf())

abstract class SymbolGroupMatcher(val name: String) {
    abstract fun match(symbols: List<Symbol>, offset: Int): SymbolGroup
}

class LineEndingSymbolGroupMatcher(): SymbolGroupMatcher("line ending") {

    override fun match(symbols: List<Symbol>, offset: Int): SymbolGroup {
        var firstSymbol: Symbol? = null;
        for(symbol in symbols.filter { it.position >= offset }) {
            if(symbol.classes.any { it.javaClass == LineEndingCharacter::class.java }) {
                if(firstSymbol != null) {
                    if(symbol.codePoint == 0x0a) {
                        return SymbolGroup(symbol.position - 1, symbol.position, name.toUpperCase().replace(" ", "_"), listOf(firstSymbol, symbol))
                    } else {
                        return SymbolGroup(symbol.position - 1, symbol.position - 1, name.toUpperCase().replace(" ", "_"), listOf(firstSymbol))
                    }
                } else if(symbol.codePoint == 0x0a) {
                    return SymbolGroup(symbol.position, symbol.position, name.toUpperCase().replace(" ", "_"), listOf(symbol))
                } else if (symbol.codePoint == 0x0d) {
                    firstSymbol = symbol
                }
            }
        }
        if(firstSymbol != null) {
            return SymbolGroup(firstSymbol.position - 1, firstSymbol.position - 1, name.toUpperCase().replace(" ", "_"), listOf(firstSymbol))
        }
        return NullSymbolGroup()
    }

}

fun List<Symbol>.forEachIndexed(begin: Int, action: (Int, Symbol) -> Unit) {
    this.forEachIndexed { i, symbol ->
        if(i >= begin)
            action(i, symbol)
    }
}