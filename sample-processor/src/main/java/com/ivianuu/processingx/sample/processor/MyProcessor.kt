/*
 * Copyright 2018 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.processingx.sample.processor

import com.google.auto.service.AutoService
import com.google.common.collect.SetMultimap
import com.ivianuu.processingx.ProcessingStep
import com.ivianuu.processingx.StepProcessor
import com.ivianuu.processingx.sample.MyAnnotation
import me.eugeniomarletti.kotlin.metadata.KotlinClassMetadata
import me.eugeniomarletti.kotlin.metadata.isDelegated
import me.eugeniomarletti.kotlin.metadata.kotlinMetadata
import me.eugeniomarletti.kotlin.metadata.proto
import javax.annotation.processing.Processor
import javax.lang.model.element.Element

/**
 * @author Manuel Wrage (IVIanuu)
 */
@AutoService(Processor::class)
class MyProcessor : StepProcessor() {

    override fun initSteps() = emptySet<ProcessingStep>()

}

class TestStep : ProcessingStep {

    override fun annotations() = setOf(MyAnnotation::class.java)

    override fun process(elementsByAnnotation: SetMultimap<Class<out Annotation>, Element>): Set<Element> {
        elementsByAnnotation[MyAnnotation::class.java]
            .forEach {
                val meta = it.kotlinMetadata as? KotlinClassMetadata ?: return@forEach
                val properties = meta.data.proto.propertyList
                properties.forEach {
                    it.isDelegated
                }
            }

        return emptySet()
    }

}

