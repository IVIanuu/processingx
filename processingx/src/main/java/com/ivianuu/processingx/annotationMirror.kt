package com.ivianuu.processingx

import com.google.auto.common.AnnotationMirrors
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.type.TypeMirror

operator fun AnnotationMirror.get(name: String): AnnotationValue =
        AnnotationMirrors.getAnnotationValue(this, name)

fun AnnotationMirror.getOrNull(name: String): AnnotationValue? = try {
    get(name)
} catch (e: Exception) {
    null
}

fun <T> AnnotationMirror.getAs(name: String): T = get(name).value()

fun <T> AnnotationMirror.getAsOrNull(name: String): T? =
    get(name).valueOrNull<T>()

fun AnnotationMirror.getAsTypeList(name: String): List<TypeMirror> =
        get(name).asTypeListValue()

fun AnnotationMirror.getAsTypeListOrNull(name: String): List<TypeMirror>? =
        get(name).asTypeListValueOrNull()

fun AnnotationMirror.getAsStringList(name: String): List<String> =
        get(name).asStringListValue()

fun AnnotationMirror.getAsStringListOrNull(name: String): List<String>? =
        get(name).asStringListValueOrNull()

fun AnnotationMirror.getAsType(name: String): TypeMirror =
        get(name).asTypeValue()

fun AnnotationMirror.getAsTypeOrNull(name: String): TypeMirror? =
        get(name).asTypeValueOrNull()