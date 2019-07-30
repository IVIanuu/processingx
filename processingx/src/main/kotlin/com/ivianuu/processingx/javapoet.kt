package com.ivianuu.processingx

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import com.squareup.javapoet.WildcardTypeName
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType
import javax.lang.model.element.TypeElement
import javax.lang.model.element.TypeParameterElement
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVariable
import kotlin.reflect.KClass

fun ParameterizedType.asJavaParameterizedTypeName(): ParameterizedTypeName =
    ParameterizedTypeName.get(this)

fun TypeMirror.asJavaTypeName(): TypeName = TypeName.get(this)

fun KClass<*>.asJavaTypeName(): TypeName = java.asJavaTypeName()

fun Class<*>.asJavaTypeName(): TypeName = TypeName.get(this)

fun Type.asJavaTypeName(): TypeName = TypeName.get(this)

fun KClass<*>.asJavaClassName(): ClassName = java.asJavaClassName()

fun Class<*>.asJavaClassName(): ClassName = ClassName.get(this)

fun TypeElement.asJavaClassName(): ClassName = ClassName.get(this)

fun TypeVariable.asJavaTypeVariableName(): TypeVariableName =
    (asElement() as TypeParameterElement).asJavaTypeVariableName()

fun TypeParameterElement.asJavaTypeVariableName(): TypeVariableName {
    val name = simpleName.toString()
    val boundsTypeNames = bounds.map(TypeMirror::asJavaTypeName).toTypedArray()
    return TypeVariableName.get(name, *boundsTypeNames)
}

fun javax.lang.model.type.WildcardType.asJavaWildcardTypeName(): TypeName =
    WildcardTypeName.get(this)

fun WildcardType.asJavaWildcardTypeName(): TypeName = WildcardTypeName.get(this)