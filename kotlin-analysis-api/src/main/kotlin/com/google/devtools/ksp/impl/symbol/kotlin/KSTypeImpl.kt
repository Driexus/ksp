/*
 * Copyright 2022 Google LLC
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
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

package com.google.devtools.ksp.impl.symbol.kotlin

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeArgument
import com.google.devtools.ksp.symbol.Nullability
import org.jetbrains.kotlin.analysis.api.types.KtType
import org.jetbrains.kotlin.analysis.api.types.KtTypeNullability

class KSTypeImpl(private val type: KtType) : KSType {
    override val declaration: KSDeclaration
        get() = TODO("Not yet implemented")
    override val nullability: Nullability by lazy {
        if (type.nullability == KtTypeNullability.NON_NULLABLE) {
            Nullability.NOT_NULL
        } else {
            Nullability.NULLABLE
        }
    }
    override val arguments: List<KSTypeArgument>
        get() = TODO("Not yet implemented")
    override val annotations: Sequence<KSAnnotation>
        get() = TODO("Not yet implemented")

    override fun isAssignableFrom(that: KSType): Boolean {
        TODO("Not yet implemented")
    }

    override fun isMutabilityFlexible(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isCovarianceFlexible(): Boolean {
        TODO("Not yet implemented")
    }

    override fun replace(arguments: List<KSTypeArgument>): KSType {
        TODO("Not yet implemented")
    }

    override fun starProjection(): KSType {
        TODO("Not yet implemented")
    }

    override fun makeNullable(): KSType {
        TODO("Not yet implemented")
    }

    override fun makeNotNullable(): KSType {
        TODO("Not yet implemented")
    }

    override val isMarkedNullable: Boolean
        get() = TODO("Not yet implemented")
    override val isError: Boolean
        get() = TODO("Not yet implemented")
    override val isFunctionType: Boolean
        get() = TODO("Not yet implemented")
    override val isSuspendFunctionType: Boolean
        get() = TODO("Not yet implemented")
}
