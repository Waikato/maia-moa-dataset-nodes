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
package maia.topology.node.moa

import moa.streams.InstanceStream
import maia.configure.Configurable
import maia.ml.dataset.DataRow
import maia.ml.dataset.DataStream
import maia.ml.dataset.moa.MOADataStream
import maia.topology.NodeConfiguration
import maia.topology.node.base.LockStepTransformer

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
