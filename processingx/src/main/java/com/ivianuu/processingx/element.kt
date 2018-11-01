package com.ivianuu.processingx

import com.google.auto.common.MoreElements
import javax.lang.model.element.Element
import javax.lang.model.element.PackageElement
import kotlin.reflect.KClass

fun Element.getPackage(): PackageElement = MoreElements.getPackage(this)

inline fun <reified T : Annotation> Element.hasAnnotation() =
        hasAnnotation(T::class)

fun Element.hasAnnotation(clazz: KClass<out Annotation>) =
        MoreElements.isAnnotationPresent(this, clazz.java)

fun Element.hasAnnotations(vararg classes: KClass<out Annotation>) =
        classes.all { hasAnnotation(it) }

fun Element.getAnnotationMirror(clazz: KClass<out Annotation>) =
        MoreElements.getAnnotationMirror(this, clazz.java).get()
            ?: throw IllegalArgumentException("no annotation of type $clazz found")

fun Element.getAnnotationMirrorOrNull(clazz: KClass<out Annotation>) = try {
    getAnnotationMirror(clazz)

} catch (e: Exception) {
    null
}

inline fun <reified T : Annotation> Element.getAnnotationMirror() =
        getAnnotationMirror(T::class)

inline fun <reified T : Annotation> Element.getAnnotationMirrorOrNull() =
    getAnnotationMirrorOrNull(T::class)