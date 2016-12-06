package org.web25.markdown

abstract class Token (val length: Int){

    abstract fun getName(): String

}