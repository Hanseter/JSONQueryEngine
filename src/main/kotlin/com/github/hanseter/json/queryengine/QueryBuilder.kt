package com.github.hanseter.json.queryengine

interface QueryBuilder<T : Query> {
	fun isComplete() : Boolean
	fun build(): T?
}