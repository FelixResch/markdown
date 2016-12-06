package org.web25.markdown.cm_0_27.tokendef

import org.web25.markdown.Symbol
import org.web25.markdown.Token
import org.web25.markdown.TokenDefinition
import org.web25.markdown.cm_0_27.token.HeadingOpenerToken

/**
 * Created by felix on 12/6/16.
 */
class HeadingOpenerTokenDefintion : TokenDefinition {

    override fun matches(symbols: List<Symbol>, offset: Int): Int {
        if(!symbols[offset].escaped && symbols[offset].printableCharacter == '#'.toInt()) {
            for (i in (offset + 1)..offset + 5) {
                if(!symbols[i].escaped) {
                    if(symbols[i].printableCharacter == ' '.toInt()) {
                        return i - offset
                    } else if (symbols[i].printableCharacter != '#'.toInt()) {
                        return -1
                    }
                } else {
                    return -1
                }
            }
        }
        return -1
    }

    override fun createToken(symbols: List<Symbol>, offset: Int, matches: Int): Token {
        return HeadingOpenerToken(matches)
    }
}