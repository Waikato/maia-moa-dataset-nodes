/*
 * MOALearnerWriter.kt
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

import moa.learners.Learner
import maia.configure.Configurable
import maia.topology.Node
import maia.topology.NodeConfiguration
import maia.topology.io.Input
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
