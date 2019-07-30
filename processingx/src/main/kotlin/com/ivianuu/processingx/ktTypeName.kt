package com.ivianuu.processingx

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.WildcardTypeName
import me.eugeniomarletti.kotlin.metadata.shadow.name.FqName
import me.eugeniomarletti.kotlin.metadata.shadow.platform.JavaToKotlinClassMap

fun TypeName.javaToKotlinType(): TypeName {
    return if (this is WildcardTypeName) {
        if (outTypes.isNotEmpty()) {
            outTypes.first().javaToKotlinType()
        } else {
            inTypes.first().javaToKotlinType()
        }
    } else if (this is ParameterizedTypeName) {
        (rawType.javaToKotlinType() as ClassName).parameterizedBy(
            *typeArguments.map { it.javaToKotlinType() }.toTypedArray()
        )
    } else {
        val className =
            JavaToKotlinClassMap.mapJavaToKotlin(FqName(toString()))?.asSingleFqName()
                ?.asString()
        if (className == null) this
        else ClassName.bestGuess(className)
    }
}