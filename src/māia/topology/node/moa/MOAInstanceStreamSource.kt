/*
 * MOAInstanceStreamSource.kt
 * Copyright (C) 2021 University of Waikato, Hamilton, New Zealand
 *
 * This file is part of MĀIA.
 *
 * MĀIA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MĀIA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MĀIA.  If not, see <https://www.gnu.org/licenses/>.
 */
package māia.topology.node.moa

import moa.streams.InstanceStream
import māia.configure.Configurable
import māia.configure.ConfigurationItem
import māia.configure.asReconfigureBlock
import māia.ml.dataset.moa.materalizeMOAClass
import māia.topology.Node
import māia.topology.NodeConfiguration
import māia.topology.io.Output

/**
 * TODO
 */
@Node.WithMetadata("TODO")
class MOAInstanceStreamSource : Node<MOAInstanceStreamSourceConfiguration> {

    @Configurable.Register<MOAInstanceStreamSource, MOAInstanceStreamSourceConfiguration>(
        MOAInstanceStreamSource::class,
        MOAInstanceStreamSourceConfiguration::class
    )
    constructor(block : MOAInstanceStreamSourceConfiguration.() -> Unit = {}) : super(block)

    constructor(config : MOAInstanceStreamSourceConfiguration) : super(config.asReconfigureBlock())

    val streams by Output<InstanceStream>()

    override suspend fun main() {
        for (index in 0 until configuration.numIterations) {
            streams.push(materalizeMOAClass(InstanceStream::class.java, configuration.config))
        }
    }

}

class MOAInstanceStreamSourceConfiguration : NodeConfiguration("moaInstanceStreamSource") {

    var config by ConfigurationItem { "" }

    var numIterations by ConfigurationItem { 1 }

}
