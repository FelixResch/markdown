package org.web25.markdown

import java.net.URI

/**
 * Created by felix on 12/6/16.
 */
interface Implementation : Extension {

    fun getSpecURL(): URI
}