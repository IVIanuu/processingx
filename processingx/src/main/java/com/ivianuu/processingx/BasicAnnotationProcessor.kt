package com.ivianuu.processingx

/**
import com.google.auto.common.SuperficialValidation.validateElement
import com.google.common.base.Ascii
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import com.google.common.collect.Iterables.transform
import com.google.common.collect.Sets
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind.PACKAGE
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.PackageElement
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.SimpleElementVisitor6
import javax.tools.Diagnostic.Kind.ERROR
import kotlin.reflect.KClass

abstract class BasicAnnotationProcessor : AbstractProcessor() {

private val deferredElementNames = LinkedHashSet<ElementName>()
private val elementsDeferredBySteps = LinkedHashMap<ProcessingStep, LinkedHashSet<ElementName>>()

private lateinit var elements: Elements
private lateinit var messager: Messager
private lateinit var steps: List<ProcessingStep>

private val supportedAnnotationClasses: Set<KClass<out Annotation>>
get() = steps.flatMap { it.annotations }.toSet()

override fun init(processingEnv: ProcessingEnvironment) {
super.init(processingEnv)
this.elements = processingEnv.elementUtils
this.messager = processingEnv.messager
this.steps = initSteps().toList()
}

protected abstract fun initSteps(): Iterable<ProcessingStep>

protected open fun postRound(roundEnv: RoundEnvironment) {
}

override fun getSupportedAnnotationTypes(): ImmutableSet<String> {
val builder = ImmutableSet.builder<String>()
for (annotationClass in supportedAnnotationClasses) {
builder.add(annotationClass.java.canonicalName)
}
return builder.build()
}

override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
val deferredElements = deferredElements()

deferredElementNames.clear()

// If this is the last round, report all of the missing elements
if (roundEnv.processingOver()) {
postRound(roundEnv)
reportMissingElements(deferredElements, elementsDeferredBySteps.flatMap { it.value })
return false
}

process(validElements(deferredElements, roundEnv))

postRound(roundEnv)

return false
}

/**
 * Returns the previously deferred elements.
*/
private fun deferredElements(): Map<String, Element?> {
val deferredElements = mutableMapOf<String, Element?>()
for (elementName in deferredElementNames) {
deferredElements[elementName.name] = elementName.getElement(elements)
}

return deferredElements
}

private fun reportMissingElements(
missingElements: Map<String, Element?>,
missingElementNames: Collection<ElementName>
) {
var missingElements = missingElements
if (!missingElementNames.isEmpty()) {
val allMissingElements = ImmutableMap.builder<String, Element?>()
allMissingElements.putAll(missingElements)
for (missingElement in missingElementNames) {
if (!missingElements.containsKey(missingElement.name)) {
allMissingElements.put(
missingElement.name,
missingElement.getElement(elements)
)
}
}
missingElements = allMissingElements.build()
}
for (missingElementEntry in missingElements.entries) {
val missingElement = missingElementEntry.value
if (missingElement != null) {
processingEnv
.messager
.printMessage(
ERROR,
processingErrorMessage(
"this " + Ascii.toLowerCase(missingElement.kind.name)
),
missingElement
)
} else {
processingEnv
.messager
.printMessage(ERROR, processingErrorMessage(missingElementEntry.key))
}
}
}

private fun processingErrorMessage(target: String): String {
return String.format(
"[%s:MiscError] %s was unable to process %s because not all of its dependencies could be "
+ "resolved. Check for compilation errors or a circular dependency with generated "
+ "code.",
javaClass.simpleName,
javaClass.canonicalName,
target
)
}

/**
 * Returns the valid annotated elements contained in all of the deferred elements. If none are
 * found for a deferred element, defers it again.
*/
private fun validElements(
deferredElements: Map<String, Element?>,
roundEnv: RoundEnvironment
): Map<KClass<out Annotation>, Set<Element>> {
val deferredElementsByAnnotation = mutableMapOf<KClass<out Annotation>, MutableSet<Element>>()

for (deferredTypeElementEntry in deferredElements.entries) {
val deferredElement = deferredTypeElementEntry.value
if (deferredElement != null) {
findAnnotatedElements(
deferredElement,
supportedAnnotationClasses,
deferredElementsByAnnotation
)
} else {
deferredElementNames.add(ElementName.forTypeName(deferredTypeElementEntry.key))
}
}

val validElements = mutableMapOf<KClass<out Annotation>, MutableSet<Element>>()

val validElementNames = LinkedHashSet<ElementName>()

// Look at the elements we've found and the new elements from this round and validate them.
for (annotationClass in supportedAnnotationClasses) {
// This should just call roundEnv.getElementsAnnotatedWith(Class) directly, but there is a bug
// in some versions of eclipse that cause that method to crash.
val annotationType = elements.getTypeElement(annotationClass.java.canonicalName)
val elementsAnnotatedWith = if ((annotationType == null))
ImmutableSet.of()
else
roundEnv.getElementsAnnotatedWith(annotationType)
for (annotatedElement in Sets.union(
elementsAnnotatedWith,
deferredElementsByAnnotation[annotationClass] ?: emptySet()
)) {
if (annotatedElement.kind == PACKAGE) {
val annotatedPackageElement = annotatedElement as PackageElement
val annotatedPackageName =
ElementName.forPackageName(annotatedPackageElement.qualifiedName.toString())
val validPackage =
(validElementNames.contains(annotatedPackageName) || ((!deferredElementNames.contains(
annotatedPackageName
) && validateElement(annotatedPackageElement))))
if (validPackage) {
validElements.getOrPut(annotationClass) { mutableSetOf() }
.add(annotatedPackageElement)
validElementNames.add(annotatedPackageName)
} else {
deferredElementNames.add(annotatedPackageName)
}
} else {
val enclosingType = getEnclosingType(annotatedElement)
val enclosingTypeName =
ElementName.forTypeName(enclosingType.qualifiedName.toString())
val validEnclosingType =
(validElementNames.contains(enclosingTypeName) || ((!deferredElementNames.contains(
enclosingTypeName
) && validateElement(enclosingType))))
if (validEnclosingType) {
validElements.getOrPut(annotationClass) { mutableSetOf() }
.add(annotatedElement)
validElementNames.add(enclosingTypeName)
} else {
deferredElementNames.add(enclosingTypeName)
}
}
}
}

return validElements
}

/** Processes the valid elements, including those previously deferred by each step.  */
private fun process(validElements: Map<KClass<out Annotation>, Set<Element>>) {
for (step in steps) {
val stepElements = mutableMapOf<KClass<out Annotation>, Set<Element>>()

elementsDeferredBySteps[step]?.let { stepElements.putAll(indexByAnnotation(it)) }
stepElements.putAll(validElements.filterKeys { step.annotations.contains(it) })

if (stepElements.isEmpty()) {
elementsDeferredBySteps.remove(step)
} else {
val rejectedElements = step.process(stepElements)
.map { ElementName.forAnnotatedElement(it) }

elementsDeferredBySteps.replace(
step,
LinkedHashSet<ElementName>().apply {
addAll(rejectedElements)
}
)
}
}
}

private fun indexByAnnotation(
annotatedElements: Set<ElementName>
): Map<KClass<out Annotation>, Set<Element>> {
val supportedAnnotationClasses = supportedAnnotationClasses
val deferredElements = mutableMapOf<KClass<out Annotation>, MutableSet<Element>>()

annotatedElements
.mapNotNull { it.getElement(elements) }
.forEach { findAnnotatedElements(it, supportedAnnotationClasses, deferredElements) }

return deferredElements
}

interface ProcessingStep {
val annotations: Set<KClass<out  Annotation>>

fun process(
elementsByAnnotation: Map<KClass<out Annotation>, Set<Element>>
): Set<Element>
}

private data class ElementName(
private val kind: Kind,
val name: String
) {

private enum class Kind {
PACKAGE_NAME,
TYPE_NAME
}

fun getElement(elements: Elements): Element? {
return if (kind == Kind.PACKAGE_NAME) {
elements.getPackageElement(name)
} else {
elements.getTypeElement(name)
}
}

companion object {

/**
 * An [ElementName] for a package.
*/
fun forPackageName(packageName: String): ElementName {
return ElementName(Kind.PACKAGE_NAME, packageName)
}

/**
 * An [ElementName] for a type.
*/
fun forTypeName(typeName: String): ElementName {
return ElementName(Kind.TYPE_NAME, typeName)
}

/**
 * An [ElementName] for an annotated element. If `element` is a package, uses the
 * fully qualified name of the package. If it's a type, uses its fully qualified name.
 * Otherwise, uses the fully-qualified name of the nearest enclosing type.
*/
fun forAnnotatedElement(element: Element): ElementName {
return if (element.kind == PACKAGE)
ElementName.forPackageName((element as PackageElement).qualifiedName.toString())
else
ElementName.forTypeName(getEnclosingType(element).qualifiedName.toString())
}
}
}
}

private fun findAnnotatedElements(
element: Element,
annotationClasses: Set<KClass<out Annotation>>,
annotatedElements: MutableMap<KClass<out Annotation>, MutableSet<Element>>
) {
for (enclosedElement in element.enclosedElements) {
if (!enclosedElement.kind.isClass && !enclosedElement.kind.isInterface) {
findAnnotatedElements(enclosedElement, annotationClasses, annotatedElements)
}
}

// element.getEnclosedElements() does NOT return parameter elements
if (element is ExecutableElement) {
for (parameterElement in element.parameters) {
findAnnotatedElements(parameterElement, annotationClasses, annotatedElements)
}
}
for (annotationClass in annotationClasses) {
if (element.hasAnnotation(annotationClass)) {
annotatedElements.getOrPut(annotationClass) { mutableSetOf() }
.add(element)
}
}
}

/**
 * Returns the nearest enclosing [TypeElement] to the current element, throwing
 * an [IllegalArgumentException] if the provided [Element] is a
 * [PackageElement] or is otherwise not enclosed by a type.
*/
// TODO(cgruber) move to MoreElements and make public.
private fun getEnclosingType(element: Element): TypeElement {
return element.accept(object : SimpleElementVisitor6<TypeElement, Void>() {
override fun defaultAction(e: Element?, p: Void?): TypeElement? {
return e?.enclosingElement?.accept(this, p)
}

override fun visitType(e: TypeElement?, p: Void?): TypeElement? {
return e
}

override fun visitPackage(e: PackageElement?, p: Void?): TypeElement? {
throw IllegalArgumentException()
}
}, null)
}*/