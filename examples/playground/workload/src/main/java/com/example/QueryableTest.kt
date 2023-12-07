package com.example

import MockRef
import SprayQueryBuilderTest
import SprayQueryBuilderTest.SprayProperty.*

class QueryableTest {
    init {
        val sprayQuery = SprayQueryBuilderTest().whereEqualTo(Name, "3").whereEqualTo(Age, 7).build()
        sprayQuery.applyQuery(MockRef()).get {

        }
    }
}
