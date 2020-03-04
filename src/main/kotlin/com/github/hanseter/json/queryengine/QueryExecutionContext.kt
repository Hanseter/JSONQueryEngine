package com.github.hanseter.json.queryengine

interface QueryExecutionContext<T : QuerieableData> {
	fun getNextElement(): T?
	fun addMatch(match: T)
	fun executionFinished()
}