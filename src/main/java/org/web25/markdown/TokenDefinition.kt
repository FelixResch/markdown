package org.web25.markdown

interface TokenDefinition {

    /**
     * This method is used to check whether a token definition matches the given symbols
     *
     * @param symbols The list of symbols that should be tokenized
     * @param offset The position the definition should try to start matching
     * @return the amount of symbols this definition matches, smaller than 0 for no match
     */
    fun matches(symbols: List<Symbol>, offset: Int): Int

    /**
     * Creates a token from the symbols provided in symbols and consumes them.
     *
     * For the compiler to work properly it is necessary for match() and createToken() to consume the same amount of symbols
     *
     * @param symbols The list of symbols that should be tokenized
     * @param offset The position the definition should try to start matching
     * @return the token this definition matches
     */
    fun createToken(symbols: List<Symbol>, offset: Int, matches: Int): Token

}

