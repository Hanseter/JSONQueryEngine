package com.github.hanseter.json.queryengine

import java.lang.RuntimeException

interface QueryBuilder<T : Query> {
	fun isComplete() : Boolean
	fun build(): T?
}