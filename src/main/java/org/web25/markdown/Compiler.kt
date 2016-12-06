package org.web25.markdown

import org.slf4j.LoggerFactory
import org.web25.markdown.cm_0_27.Commonmark

/**
 * Created by felix on 12/6/16.
 */
class Compiler private constructor(implementation: Implementation) {

    private val log = LoggerFactory.getLogger("Markdown")

    val tokenizer: Tokenizer = Tokenizer()
    val symbolizer: Symbolizer = Symbolizer()

    companion object {

        fun createDefault(): Compiler {
            return Compiler(Commonmark())
        }
    }

    init {
        log.info("Loading implementation " + implementation.getName() + " [" + implementation.getVersion() + "]")
        log.debug("For further information see " + implementation.getSpecURL().toString())
        implementation.registerTokenDefinitions(tokenizer)
        log.debug("Loaded " + tokenizer.tokenDefinitions.size + " token definition")
        tokenizer.tokenDefinitions.forEach {
            log.debug("\tLoaded " + it.javaClass.name)
        }
    }

}