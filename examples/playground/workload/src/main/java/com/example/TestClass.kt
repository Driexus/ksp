package com.example

import com.example.annotation.Builder
import com.example.annotation.FirestoreQueryable

//@FirestoreQueryable
data class TestClass (
    val owner: String? = null,
    val gymID: String? = null,
    val routeID: String? = null,
    val rating: Double? = null
)
