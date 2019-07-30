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