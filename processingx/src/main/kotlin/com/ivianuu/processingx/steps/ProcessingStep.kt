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

package com.ivianuu.processingx.steps

import com.google.common.collect.SetMultimap
import com.ivianuu.processingx.ProcessingEnvHolder
import com.ivianuu.processingx.validate
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import kotlin.reflect.KClass

/**
 * @author Manuel Wrage (IVIanuu)
 */
abstract class ProcessingStep : ProcessingEnvHolder {

    override val processingEnv: ProcessingEnvironment
        get() = _processingEnv ?: error("cannot only accessed after init() was called")
    private var _processingEnv: ProcessingEnvironment? = null

    protected open fun init() {
    }

    abstract fun annotations(): Set<KClass<out Annotation>>

    open fun validate(
        annotationClass: KClass<out Annotation>,
        element: Element
    ) = if (element.kind == ElementKind.PACKAGE) {
        element.validate()
    } else {
        element.getEnclosingType().validate()
    }

    abstract fun process(
        elementsByAnnotation: SetMultimap<KClass<out Annotation>, Element>
    ): Set<Element>

    open fun postRound(
        processingOver: Boolean
    ) {
    }

    internal fun performInit(processingEnv: ProcessingEnvironment) {
        _processingEnv = processingEnv
        init()
    }
}