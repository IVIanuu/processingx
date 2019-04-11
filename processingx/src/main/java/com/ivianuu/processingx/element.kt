package com.ivianuu.processingx

import com.google.auto.common.AnnotationMirrors
import com.google.auto.common.MoreElements
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.Element
import javax.lang.model.element.PackageElement
import kotlin.reflect.KClass

fun Element.getPackage(): PackageElement = MoreElements.getPackage(this)

inline fun <reified T : Annotation> Element.hasAnnotation(): Boolean =
        hasAnnotation(T::class)

fun Element.hasAnnotation(clazz: KClass<out Annotation>): Boolean =
    hasAnnotation(clazz.java)

fun Element.hasAnnotation(clazz: Class<out Annotation>): Boolean =
    MoreElements.isAnnotationPresent(this, clazz)

fun Element.hasAnnotations(vararg classes: KClass<out Annotation>): Boolean =
    hasAnnotations(*classes.map { it.java }.toTypedArray())

fun Element.hasAnnotations(vararg classes: Class<out Annotation>): Boolean =
    classes.all { hasAnnotation(it) }

fun Element.getAnnotationMirror(clazz: KClass<out Annotation>): AnnotationMirror =
    getAnnotationMirror(clazz.java)

fun Element.getAnnotationMirror(clazz: Class<out Annotation>): AnnotationMirror =
    MoreElements.getAnnotationMirror(this, clazz).get()
        ?: throw IllegalArgumentException("no annotation of type $clazz found")

fun Element.getAnnotationMirrorOrNull(clazz: KClass<out Annotation>): AnnotationMirror? =
    getAnnotationMirrorOrNull(clazz.java)

fun Element.getAnnotationMirrorOrNull(clazz: Class<out Annotation>): AnnotationMirror? = try {
    getAnnotationMirror(clazz)
} catch (e: Exception) {
    null
}

inline fun <reified T : Annotation> Element.getAnnotationMirror(): AnnotationMirror =
        getAnnotationMirror(T::class)

inline fun <reified T : Annotation> Element.getAnnotationMirrorOrNull(): AnnotationMirror? =
    getAnnotationMirrorOrNull(T::class)

inline fun <reified T : Annotation> Element.getAnnotatedAnnotations(): Set<AnnotationMirror> =
    getAnnotatedAnnotations(T::class)

fun Element.getAnnotatedAnnotations(clazz: KClass<out Annotation>): Set<AnnotationMirror> =
    getAnnotatedAnnotations(clazz.java)

fun Element.getAnnotatedAnnotations(clazz: Class<out Annotation>): Set<AnnotationMirror> =
    AnnotationMirrors.getAnnotatedAnnotations(this, clazz).toSet()