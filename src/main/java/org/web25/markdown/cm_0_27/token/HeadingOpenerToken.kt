package org.web25.markdown.cm_0_27.token

import org.web25.markdown.Token

/**
 * Created by felix on 12/6/16.
 */
class HeadingOpenerToken(length: Int) : Token(length) {

    override fun getName(): String = "heading_opener"


}