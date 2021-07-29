package māia.topology.node.moa

import moa.streams.InstanceStream
import māia.configure.Configurable
import māia.ml.dataset.DataRow
import māia.ml.dataset.DataStream
import māia.ml.dataset.moa.MOADataStream
import māia.topology.NodeConfiguration
import māia.topology.node.base.LockStepTransformer

/**
 * TODO: What class does.
 *
 * @author Corey Sterling (csterlin at waikato dot ac dot nz)
 */
class InstanceStreamToDataStream : LockStepTransformer<NodeConfiguration, InstanceStream, DataStream<DataRow>> {

    @Configurable.Register<InstanceStreamToDataStream, NodeConfiguration>(
        InstanceStreamToDataStream::class, NodeConfiguration::class)
    constructor(block : NodeConfiguration.() -> Unit = {}) : super(block)

    override suspend fun transformSingle(
        item: InstanceStream
    ): DataStream<DataRow> {
        return MOADataStream(item)
    }

}
