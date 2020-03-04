package com.github.hanseter.json.queryengine

import org.json.JSONObject

class OrQuery(val queries: List<Query>) : Query {

	override fun matches(data: QuerieableData): Boolean = queries.any { it.matches(data) }

	public class OrQueryBuilder : QueryBuilder<OrQuery> {
		private var subQueries = listOf<QueryBuilder<*>>()

		fun addSubQuery(query: QueryBuilder<*>) {
			subQueries += query
		}

		fun removeSubQuery(query: QueryBuilder<*>) {
			subQueries -= query
		}

		fun setSubQueries(subQueries: List<QueryBuilder<*>>) {
			this.subQueries = subQueries.toList()
		}

		override fun isComplete(): Boolean = subQueries.isNotEmpty() && subQueries.all { it.isComplete() }

		override fun build(): OrQuery? {
			val queries = subQueries.map { it.build() }
			if (queries.any { it == null }) return null
			return OrQuery(queries.requireNoNulls())
		}
	}
}