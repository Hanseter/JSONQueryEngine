package com.github.hanseter.json.queryengine

import com.github.hanseter.json.queryengine.queries.BetweenQueryBuilder
import org.json.JSONObject
import org.junit.jupiter.api.Test

class BetweenQueryTest {
    private val objects =
        sequenceOf(
            JSONObject().put("int", 42).put("string", "foo"),
            JSONObject().put("int", 815).put("string", "bar")
                .put("nested", JSONObject().put("decimal", 1.33)),
            JSONObject().put("int", 78).put("string", "baz")
                .put("nested", JSONObject().put("decimal", 3.33))
        ).withIndex().map { (i, it) -> QuerieableDataStub(i, it) }

    @Test
    fun findsNumberElements() {
        val query =
            BetweenQueryBuilder().withAttributePath("nested/decimal").withBounds(1.0, 3.0).build()!!
        val result = objects.filter { query.matches(it) }.toList()
        assert(result.size == 1)
        assert(result.map { it.id } == listOf(1))
    }

    @Test
    fun findsStringElements() {
        val query =
            BetweenQueryBuilder().withAttributePath("string").withBounds("bar", "car").build()!!
        val result = objects.filter { query.matches(it) }.toList()
        assert(result.size == 2)
        assert(result.map { it.id } == listOf(1, 2))
    }
}