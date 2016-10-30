package org.web25.markdown.blocks.leaf

import io.femo.markdown.Tag
import io.femo.markdown.Text
import io.femo.markdown.blocks.ContainerBlock
import io.femo.markdown.blocks.LeafBlock
import io.femo.markdown.set

/**
 * Created by felix on 10/19/16.
 */

class Code(parent: ContainerBlock, var code: String, var type: String? = null) : LeafBlock(parent) {

    override fun name(): String = "code"

    override fun nodeType(): String = Code::class.qualifiedName!!

    override fun renderTags(): Tag {
        val tCode = Tag("code")
                .add(Text(code))
        if(type != null) {
            tCode.set("class", "language-$type")
        }
        return Tag("pre").add(tCode)
    }

}