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

@file:Suppress("ClassName", "unused")

object Build {
    const val applicationId = "com.ivianuu.processingx.sample"
    const val buildToolsVersion = "28.0.3"

    const val compileSdk = 29
    const val minSdk = 14
    const val targetSdk = 29
    const val versionCode = 1
    const val versionName = "0.0.1"
}

object Publishing {
    const val groupId = "com.ivianuu.processingx"
    const val vcsUrl = "https://github.com/IVIanuu/processing-x"
    const val version = "${Build.versionName}-dev4"
}

object Versions {
    const val androidGradlePlugin = "3.4.1"
    const val androidxAppCompat = "1.1.0-alpha04"

    const val autoCommon = "0.10"
    const val autoService = "1.0-rc5"

    const val bintray = "1.8.4"

    const val gradleIncapHelper = "0.2"

    const val javaPoet = "1.11.1"
    const val kotlin = "1.3.40"
    const val kotlinMetaData = "1.4.0"
    const val kotlinPoet = "1.3.0"
    const val mavenGradle = "2.1"
}

object Deps {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"

    const val androidxAppCompat = "androidx.appcompat:appcompat:${Versions.androidxAppCompat}"
    const val autoCommon = "com.google.auto:auto-common:${Versions.autoCommon}"
    const val autoService = "com.google.auto.service:auto-service:${Versions.autoService}"

    const val bintrayGradlePlugin =
        "com.jfrog.bintray.gradle:gradle-bintray-plugin:${Versions.bintray}"

    const val gradleIncapHelper = "net.ltgt.gradle.incap:incap:${Versions.gradleIncapHelper}"
    const val gradleIncapHelperProcessor =
        "net.ltgt.gradle.incap:incap-processor:${Versions.gradleIncapHelper}"

    const val javaPoet = "com.squareup:javapoet:${Versions.javaPoet}"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    const val kotlinMetaData =
        "me.eugeniomarletti.kotlin.metadata:kotlin-metadata:${Versions.kotlinMetaData}"

    const val kotlinPoet = "com.squareup:kotlinpoet:${Versions.kotlinPoet}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    const val mavenGradlePlugin =
        "com.github.dcendents:android-maven-gradle-plugin:${Versions.mavenGradle}"
}