package io.femo.markdown

import org.slf4j.LoggerFactory
import java.util.*

private val logger = LoggerFactory.getLogger("Markdown")

/**
 * An incremental markdown compiler, that emmits an MarkdownDocument, that can be updated.
 */
class MarkdownCompiler() {

    init {
        logger.info("Initializing Markdown Compiler implementing " + standard() + " [" + version() + "]")
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
}

class MarkdownDocument(compiler: MarkdownCompiler, document: String) {

    val compiler: MarkdownCompiler;
    private var document: String? = null;
    private val symbols: MutableList<Symbol> = ArrayList()

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
            document = update.text
        }
    }

}

data class Update (val updateType: UpdateType, val start: Int, val end: Int, val text: String)

enum class UpdateType {
    INSERT, REMOVE, REPLACE
}