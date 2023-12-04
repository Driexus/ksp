package com.example

import com.example.annotation.FirestoreQueryable
import java.sql.Timestamp

@FirestoreQueryable
data class GymData constructor(
    val name: String? = null,
    val owner: String? = null,
    val date: Timestamp? = null,

    val public : Boolean? = null,
    val number_of_routes : Int? = null,
    val grade_distribution : Map<String, Int>? = null,

    val lat : Double? = null,
    val lon : Double? = null,
    val parking_lat : Double? = null,
    val parking_lon : Double? = null
)
