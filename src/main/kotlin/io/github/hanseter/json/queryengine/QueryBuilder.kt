package io.github.hanseter.json.queryengine

interface QueryBuilder {
	fun isComplete() : Boolean
	fun build(): Query?
}