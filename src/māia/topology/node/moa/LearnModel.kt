/*
 * LearnModel.kt
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

import com.yahoo.labs.samoa.instances.Instance
import moa.classifiers.MultiClassClassifier
import moa.core.Example
import moa.learners.Learner
import moa.options.ClassOption
import moa.streams.InstanceStream
import moa.tasks.NullMonitor
import māia.configure.Configurable
import māia.configure.ConfigurationItem
import māia.topology.NodeConfiguration
import māia.topology.node.base.LockStepTransformer

/**
 * TODO: What class does.
 *
 * @author Corey Sterling (csterlin at waikato dot ac dot nz)
 */
class LearnModel : LockStepTransformer<LearnModelConfiguration, InstanceStream, Learner<Example<Instance>>> {

    @Configurable.Register<LearnModel, LearnModelConfiguration>(LearnModel::class, LearnModelConfiguration::class)
    constructor(block : LearnModelConfiguration.() -> Unit = {}) : super(block)

    override suspend fun transformSingle(item: InstanceStream): Learner<Example<Instance>> {
        val monitor = NullMonitor()
        val learner = learnerOption.materializeObject(monitor, null) as Learner<Example<Instance>>
        learner.prepareForUse(monitor, null)

        learner.modelContext = item.header
        var instancesProcessed: Long = 0
        while (item.hasMoreInstances()
                && (configuration.maxInstances < 0 || instancesProcessed < configuration.maxInstances)) {
            learner.trainOnInstance(item.nextInstance())
            println("Trained on instance $instancesProcessed")
            instancesProcessed++
        }
        learner.modelContext = item.header
        return learner
    }

    val learnerOption = ClassOption("learn", 'l',
            "Classifier to train.", MultiClassClassifier::class.java, "moa.classifiers.bayes.NaiveBayes")

    init {
        learnerOption.setValueViaCLIString(configuration.config)
    }
}

class LearnModelConfiguration : NodeConfiguration() {

    var config by ConfigurationItem { "" }

    var maxInstances by ConfigurationItem { -1 }

}
