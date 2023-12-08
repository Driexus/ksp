package com.example

import MockRef
import javax.management.Query
import com.google.firebase.firestore.CollectionReference


class QueryableTest {
    /*init {
        val sprayQuery = SprayQueryBuilderModel()
            .whereEqualTo(SprayQueryBuilderModel.SprayProperty.Name, "3")
            .whereEqualTo(SprayQueryBuilderModel.SprayProperty.Age, 7).build()

        sprayQuery.applyQuery(MockRef()).get {
        }
    }*/

    inline fun <reified T: Any> test() {
        val a = mutableListOf<List<*>>()
        val ints = listOf<Int>()
        val strings = listOf<String>()
        val ts = listOf<T>()

        a.add(ints)
        a.add(strings)
        a.add(ts)
    }

    inline fun <reified T: Any> test2(value : T) {
        val a = mutableListOf<TestInterface<*>>()
        a.add(ClassOne())
        a.add(ClassTwo())
        a.add(GenericClass<T>())
        a.add(GenericClassWithArg(value))
    }

    fun firestoreTest() {
        val a : Query
    }


}

interface TestInterface <T>

class ClassOne: TestInterface<Int>
class ClassTwo: TestInterface<String>

class GenericClass<T> : TestInterface<T>

class GenericClassWithArg<T> (val value: T): TestInterface<T>
