package io.github.hanseter.json.queryengine

fun interface Query {
	fun matches(data: QueryableData) : Boolean
}