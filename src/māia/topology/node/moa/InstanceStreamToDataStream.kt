/*
 * InstanceStreamToDataStream.kt
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
