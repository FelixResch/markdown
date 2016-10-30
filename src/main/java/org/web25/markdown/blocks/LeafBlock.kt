package org.web25.markdown.blocks

/**
 * Created by felix on 10/17/16.
 */
abstract class LeafBlock(parent: ContainerBlock, incremental: Boolean = parent.incremental): Block(parent, incremental) {


}