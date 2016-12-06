package org.web25.markdown.cm_0_27

import org.web25.markdown.Implementation
import org.web25.markdown.Tokenizer
import org.web25.markdown.cm_0_27.tokendef.HeadingOpenerTokenDefintion
import java.net.URI

/**
 * Created by felix on 12/6/16.
 */

class Commonmark : Implementation {

    override fun registerTokenDefinitions(tokenizer: Tokenizer) {
        tokenizer.tokenDefinitions.add(HeadingOpenerTokenDefintion())
    }

    override fun getSpecURL(): URI = URI("http://spec.commonmark.org/0.27/")

    override fun getName(): String = "Commonmark"

    override fun getVersion(): String = "0.27"

}
