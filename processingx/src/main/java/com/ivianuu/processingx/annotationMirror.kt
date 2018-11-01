package com.ivianuu.processingx

import com.google.auto.common.AnnotationMirrors
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue

operator fun AnnotationMirror.get(name: String): AnnotationValue =
        AnnotationMirrors.getAnnotationValue(this, name)

fun AnnotationMirror.getOrNull(name: String) = try {
    get(name)
} catch (e: Exception) {
    null
}

fun AnnotationMirror.getAsTypeList(name: String) =
        get(name).asTypeListValue()

fun AnnotationMirror.getAsTypeListOrNull(name: String) =
        get(name).asTypeListValueOrNull()

fun AnnotationMirror.getAsStringList(name: String) =
        get(name).asStringListValue()

fun AnnotationMirror.getAsStringListOrNull(name: String) =
        get(name).asStringListValueOrNull()

fun AnnotationMirror.getAsType(name: String) =
        get(name).asTypeValue()

fun AnnotationMirror.getAsTypeOrNull(name: String) =
        get(name).asTypeValueOrNull()