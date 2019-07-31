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

package com.ivianuu.processingx

import java.util.*
import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

/**
 * @author Manuel Wrage (IVIanuu)
 */
interface ProcessingEnvHolder {
    val processingEnv: ProcessingEnvironment
}

val ProcessingEnvHolder.options: Map<String, String> get() = processingEnv.options

val ProcessingEnvHolder.messager: Messager get() = processingEnv.messager

val ProcessingEnvHolder.filer: Filer get() = processingEnv.filer

val ProcessingEnvHolder.elementUtils: Elements get() = processingEnv.elementUtils

val ProcessingEnvHolder.typeUtils: Types get() = processingEnv.typeUtils

val ProcessingEnvHolder.sourceVersion: SourceVersion get() = processingEnv.sourceVersion

val ProcessingEnvHolder.locale: Locale get() = processingEnv.locale