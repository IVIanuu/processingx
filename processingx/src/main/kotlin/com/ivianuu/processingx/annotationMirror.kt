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