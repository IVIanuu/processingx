package com.ivianuu.processingx

import javax.lang.model.element.AnnotationValue
import javax.lang.model.type.TypeMirror

fun <T> AnnotationValue.value(): T = (value as T)
fun <T> AnnotationValue.valueOrNull(): T? = try {
    value<T>()
} catch (e: Exception) {
    null
}

fun AnnotationValue.asTypeListValue(): List<TypeMirror> =
    (value as List<AnnotationValue>).map { it.value as TypeMirror }

fun AnnotationValue.asTypeListValueOrNull(): List<TypeMirror>? = try {
    asTypeListValue()
} catch (e: Exception) {
    null
}

fun AnnotationValue.asStringListValue(): List<String> =
    (value as List<String>)

fun AnnotationValue.asStringListValueOrNull(): List<String>? = try {
    asStringListValue()
} catch (e: Exception) {
    null
}

fun AnnotationValue.asTypeValue(): TypeMirror = value as TypeMirror

fun AnnotationValue.asTypeValueOrNull(): TypeMirror? = try {
    asTypeValue()
} catch (e: Exception) {
    null
}