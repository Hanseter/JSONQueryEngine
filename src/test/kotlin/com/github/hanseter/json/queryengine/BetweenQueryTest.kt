package com.github.hanseter.json.queryengine

import org.json.JSONObject
import org.junit.jupiter.api.Test

class BetweenQueryTest {
	private val objects =
		sequenceOf(
			JSONObject().put("int", 42).put("string", "foo"),
			JSONObject().put("int", 815).put("string", "bar").put("nested", JSONObject().put("decimal", 1.33)),
			JSONObject().put("int", 78).put("string", "baz").put("nested", JSONObject().put("decimal", 3.33))
		).withIndex().map { (i, it) -> QuerieableDataStub(i, it) }

	@Test
	fun findsNumberElements() {
		val query = BetweenQuery(AttributePath("nested.decimal"), "1", "3")
		val result = objects.filter { query.matches(it) }.toList()
		assert(result.size == 1)
		assert(result.map { it.id } == listOf(1))
	}

	@Test
	fun findsStringElements() {
		val query = BetweenQuery(AttributePath("string"), "bar", "car")
		val result = objects.filter { query.matches(it) }.toList()
		assert(result.size == 2)
		assert(result.map { it.id } == listOf(1, 2)) 
	}
}