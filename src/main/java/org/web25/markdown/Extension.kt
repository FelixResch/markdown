package org.web25.markdown

/**
 * Created by felix on 12/6/16.
 */
interface Extension {

    fun getName(): String
    fun getVersion(): String

    fun registerTokenDefinitions(tokenizer: Tokenizer)
}