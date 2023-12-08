package com.example

import SprayData
import SprayQueryBuilder
import org.junit.jupiter.api.Assertions.*
import com.example.SprayProperty.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@Suppress("FunctionName")
class SprayQueryBuilderModelTest {
    private lateinit var builder : SprayQueryBuilder<SprayData>
    private lateinit var builder2 : SprayQueryBuilder<SprayData>

    @BeforeEach
    fun init() {
        builder = SprayQueryBuilder(SprayData::class)
        builder2 = SprayQueryBuilder(SprayData::class)
    }
    @Test
    fun `two empty queries should be equal`() {
        val sprayQuery = builder.build()
        val sprayQuery2 = builder2.build()
        assertEquals(sprayQuery, sprayQuery2)
    }

    @Test
    fun `two queries with operations in same order should be equal`() {
        val sprayQuery = builder.whereEqualTo(Name, "John").build()
        val sprayQuery2 = builder2.whereEqualTo(Name, "John").build()
        assertEquals(sprayQuery, sprayQuery2)
    }

    @Test
    fun `two queries with different operations should not be equal`() {
        val sprayQuery = builder.build()
        val sprayQuery2 = builder2.whereEqualTo(Name, "John").build()
        assertNotEquals(sprayQuery, sprayQuery2)
    }

    @Test
    fun `two queries with same operations but different compare values should not be equal`() {
        val sprayQuery = builder.whereEqualTo(Name, "John").build()
        val sprayQuery2 = builder2.whereEqualTo(Name, "Jane").build()
        assertNotEquals(sprayQuery, sprayQuery2)
    }

    @Test
    fun `two queries with same operations in different order should be equal`() {
        val sprayQuery = builder.whereEqualTo(Age, 26).whereEqualTo(Name, "Jane").build()
        val sprayQuery2 = builder2.whereEqualTo(Name, "Jane").whereEqualTo(Age, 26).build()
        assertEquals(sprayQuery.hashCode(), sprayQuery2.hashCode())
    }
}
