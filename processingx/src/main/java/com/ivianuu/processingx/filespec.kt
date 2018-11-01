package com.ivianuu.processingx

import com.squareup.kotlinpoet.FileSpec
import java.io.File
import javax.annotation.processing.ProcessingEnvironment

fun FileSpec.write(processingEnv: ProcessingEnvironment) {
    val path = processingEnv.options["kapt.kotlin.generated"]?.replace("kaptKotlin", "kapt")!!
    writeTo(File(path))
}