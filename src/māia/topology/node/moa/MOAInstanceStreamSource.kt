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
