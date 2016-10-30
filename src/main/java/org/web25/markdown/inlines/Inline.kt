package org.web25.markdown.inlines

import io.femo.markdown.blocks.Block
import io.femo.markdown.Node

/**
 * Created by felix on 10/17/16.
 */
abstract class Inline (parent: Node?, incremental: Boolean = (if (parent != null) parent.incremental else true)): Node(parent, incremental) {



}
