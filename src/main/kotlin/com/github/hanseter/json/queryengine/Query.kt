package com.github.hanseter.json.queryengine

interface Query {
	fun matches(data: QueryableData) : Boolean
}