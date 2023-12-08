package com.example

import Property
import QueryableType
import SprayData
import SprayQueryBuilder

/*
class SprayQueryBuilderModel: SprayQueryBuilder<SprayData>(SprayData::class) {
    // TODO: Handle casting exception
    override fun <T : Any, U : QueryableType<out T>> propertyToString(property: Property<SprayData, T, U>): String {
        return when (property as SprayProperty<*,*>) {
            is SprayProperty.Name -> "name"
            is SprayProperty.Age -> "age"
        }
    }
*/

    /*sealed class SprayProperty<T: Any, U: QueryableType<T>> : Property<SprayData, T, U> {
        data object Name : SprayProperty<String, QueryableType.StringQueryable>() {
            override fun toDBString(): String = "name"
        }

        data object Age : SprayProperty<Int, QueryableType.IntQueryable>() {
            override fun toDBString(): String = "age"
        }
    }*/

sealed class SprayProperty<T: Any> : Property<SprayData, T> {
    data object Name : SprayProperty<String>() {
        override fun toDBString(): String = "name"
    }

    data object Age : SprayProperty<Int>() {
        override fun toDBString(): String = "age"
    }
}

