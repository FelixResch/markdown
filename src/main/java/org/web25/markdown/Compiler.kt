package org.web25.markdown

import org.slf4j.LoggerFactory
import sun.text.CodePointIterator
import java.util.*

private val logger = LoggerFactory.getLogger("Markdown")

/**
 * An incremental markdown compiler, that emmits an MarkdownDocument, that can be updated.
 */
class MarkdownCompiler() {

    val symbolClasses: MutableList<SymbolClass> = ArrayList();

    init {
        logger.info("Initializing Markdown Compiler implementing " + standard() + " [" + version() + "]")
        //symbolClasses.add(CharacterClass())
        symbolClasses.add(WhitespaceCharacterClass())
        symbolClasses.add(UnicodeWhitespaceCharacterClass())
        symbolClasses.add(NonWhitespaceCharacterClass())
        symbolClasses.add(SpaceCharacterClass())
        symbolClasses.add(TabCharacterClass())
        symbolClasses.add(LineEndingCharacter())
        symbolClasses.add(ASCIIPunctuationCharacterClass())
        symbolClasses.add(PunctuationCharacterClass())


        symbolClasses.forEach { logger.debug(it.name) }
    }

    /**
     * Returns the version of the markdown specification it implements
     *
     * @return the implemented version of the specification
     */
    fun version(): String = "0.26"

    /**
     * Returns the speoification it implements
     *
     * @return the name of the implemented specification
     */
    fun standard(): String = "commonmark"

    /**
     * Compiles a string to an MarkdownDocument
     */
    fun compile(document: String): MarkdownDocument {
        return MarkdownDocument(this, document)
    }

    fun classifySymbol(symbol: Symbol) {
        symbolClasses.filter { it.matches(symbol) }.forEach { symbol.classes.add(it) }
    }

    fun groupSymbols(symbols: List<Symbol>): MutableList<SymbolGroup> {
        val list = ArrayList<SymbolGroup>()
        val matcher = LineEndingSymbolGroupMatcher();
        var lineEnding: SymbolGroup
        var lIndex = 0;
        lineEnding = matcher.match(symbols, 0)
        while (lineEnding.from >= 0 && lineEnding.to >= 0 && lineEnding.to < symbols.size) {
            list.add(Line(lIndex, lineEnding.from, "LINE", symbols.subList(lIndex, lineEnding.from).toList()))
            lIndex = lineEnding.to + 1
            lineEnding = matcher.match(symbols, lIndex)
        }
        if(lineEnding.to == -1) {
            list.add(Line(lIndex, symbols.size, "LINE", symbols.subList(lIndex, symbols.size).toList()))
        }
        return list;
    }
}

class MarkdownDocument(compiler: MarkdownCompiler, document: String) {

    val compiler: MarkdownCompiler;
    private var document: String? = null
    internal val symbols: MutableList<Symbol> = ArrayList()
    internal var lines: MutableList<SymbolGroup> = ArrayList();

    init {
        this.compiler = compiler;
        update(Update(UpdateType.INSERT, 0, 0, document))
    }

    var markdown: String?
        set(value) = logger.warn("MarkdownDocument.document is read only")
        get() = document;

    fun update(update: Update) {
        logger.debug("Performing update: " + update)
        if(update.updateType == UpdateType.INSERT) {
            if(document == null) {
                document = "";
            }
            document = document!!.insertAt(update.start, update.text)
            val codePoints = document!!.codePoints()
            var before: Symbol? = null
            codePoints.forEachIndexed { i, codePoint ->
                val symbol = Symbol(codePoint, i, before)
                compiler.classifySymbol(symbol);
                symbols.add(symbol)
                if(before != null) {
                    before!!.next = symbol
                }
                before = symbol
            }
            lines = compiler.groupSymbols(symbols)
            printInfo { logger.debug(it) }
        } else if (update.updateType == UpdateType.REMOVE) {
            if(document == null) {
                document = "";
            } else {
                document = document!!.removeRange(update.start, update.end);
            }
        } else if (update.updateType == UpdateType.REPLACE) {
            if(document == null) {
                document = "";
            } else {
                document = document!!.replaceRange(update.start, update.end, update.text)
            }
        }
    }

    fun printInfo(print: (String) -> Unit) {
        symbols.forEach {
            var message = String.format("%03x ", it.position)
            message += String.format("%04x ", it.codePoint)
            if(Character.isISOControl(it.codePoint)) {
                message += ". "
            } else {
                Character.toChars(it.codePoint).forEach { message += it }
                message += " "
            }
            it.classes.forEach {
                message += it.name.toUpperCase().replace(" ", "_") + " "
            }
            print(message)
        }
        print("")
        lines.forEach {
            var message = String.format("%1c %03x %03x - ", if(it is Line && it.isBlank()) {'E'} else {' '}, it.from, it.to)
            it.symbols.forEach {
                if(Character.isISOControl(it.codePoint)) {
                    message += ". "
                } else {
                    Character.toChars(it.codePoint).forEach { message += it }
                }
            }
            print(message);
        }
    }

}

data class Update (val updateType: UpdateType, val start: Int, val end: Int, val text: String)

enum class UpdateType {
    INSERT, REMOVE, REPLACE
}

fun String.insertAt(index: Int, text: String): String {
    return this.substring(0, index) + text + this.substring(index);
}