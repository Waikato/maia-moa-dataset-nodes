package māia.topology.node.moa

import moa.learners.Learner
import māia.configure.Configurable
import māia.topology.Node
import māia.topology.NodeConfiguration
import māia.topology.io.Input
import java.lang.StringBuilder

/**
 * TODO: What class does.
 *
 * @author Corey Sterling (csterlin at waikato dot ac dot nz)
 */
class MOALearnerWriter : Node<NodeConfiguration> {

    @Configurable.Register<MOALearnerWriter, NodeConfiguration>(MOALearnerWriter::class, NodeConfiguration::class)
    constructor(block : NodeConfiguration.() -> Unit = {}) : super(block)

    val input by Input<Learner<*>>()

    override suspend fun main() {
        while (!input.isClosed) {
            val item = input.pull()
            println("Received model $item")
            val sb = StringBuilder()
            item.getDescription(sb, 0)
            println(sb)
        }
    }
}
