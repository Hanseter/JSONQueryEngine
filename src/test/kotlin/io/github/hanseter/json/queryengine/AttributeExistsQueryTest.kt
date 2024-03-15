package io.github.hanseter.json.queryengine

import com.github.hanseter.json.queryengine.queries.AttributeExistsQueryBuilder
import org.json.JSONObject
import org.junit.jupiter.api.Test

class AttributeExistsQueryTest {
	private val objects =
		sequenceOf(
			JSONObject().put("int", 42),
			JSONObject().put("int", 815).put("nested", JSONObject().put("decimal", 1.33)),
			JSONObject().put("int", 78).put("nested", JSONObject().put("decimal2", 1.33))
		).withIndex().map { (i, it) -> QuerieableDataStub(i, it) }

	@Test
	fun findsElements() {
		val query = AttributeExistsQueryBuilder().withAttributePath("nested/decimal").build()!!
		val result = objects.filter { query.matches(it) }.toList()
		assert(result.size == 1)
		assert(result.single().data.get("int") == 815)
	}

}