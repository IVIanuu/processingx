package com.ivianuu.processingx

import com.google.auto.common.AnnotationMirrors
import com.google.auto.common.MoreElements
import javax.lang.model.element.Element
import javax.lang.model.element.PackageElement
import kotlin.reflect.KClass

fun Element.getPackage(): PackageElement = MoreElements.getPackage(this)

inline fun <reified T : Annotation> Element.hasAnnotation() =
        hasAnnotation(T::class)

fun Element.hasAnnotation(clazz: KClass<out Annotation>) =
    hasAnnotation(clazz.java)

fun Element.hasAnnotation(clazz: Class<out Annotation>) =
    MoreElements.isAnnotationPresent(this, clazz)

fun Element.hasAnnotations(vararg classes: KClass<out Annotation>) =
    hasAnnotations(*classes.map { it.java }.toTypedArray())

fun Element.hasAnnotations(vararg classes: Class<out Annotation>) =
    classes.all { hasAnnotation(it) }

fun Element.getAnnotationMirror(clazz: KClass<out Annotation>) =
    getAnnotationMirror(clazz.java)

fun Element.getAnnotationMirror(clazz: Class<out Annotation>) =
    MoreElements.getAnnotationMirror(this, clazz).get()
        ?: throw IllegalArgumentException("no annotation of type $clazz found")

fun Element.getAnnotationMirrorOrNull(clazz: KClass<out Annotation>) =
    getAnnotationMirrorOrNull(clazz.java)

fun Element.getAnnotationMirrorOrNull(clazz: Class<out Annotation>) = try {
    getAnnotationMirror(clazz)
} catch (e: Exception) {
    null
}

inline fun <reified T : Annotation> Element.getAnnotationMirror() =
        getAnnotationMirror(T::class)

inline fun <reified T : Annotation> Element.getAnnotationMirrorOrNull() =
    getAnnotationMirrorOrNull(T::class)

inline fun <reified T : Annotation> Element.getAnnotatedAnnotations() =
    getAnnotatedAnnotations(T::class)

fun Element.getAnnotatedAnnotations(clazz: KClass<out Annotation>) =
    getAnnotatedAnnotations(clazz.java)

fun Element.getAnnotatedAnnotations(clazz: Class<out Annotation>) =
    AnnotationMirrors.getAnnotatedAnnotations(this, clazz)