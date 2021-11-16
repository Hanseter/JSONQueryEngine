package com.github.hanseter.json.queryengine

interface QueryExecutionContext<T : QueryableData> {
	fun getNextElement(): T?
	fun addMatch(match: T)
	fun executionFinished()
}