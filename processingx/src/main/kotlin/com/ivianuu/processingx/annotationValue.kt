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