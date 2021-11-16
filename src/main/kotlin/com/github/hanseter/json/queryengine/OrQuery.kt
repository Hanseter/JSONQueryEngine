package com.github.hanseter.json.queryengine

class OrQuery(private val queries: List<Query>) : Query {

	override fun matches(data: QueryableData): Boolean = queries.any { it.matches(data) }

	class OrQueryBuilder : QueryBuilder<OrQuery> {
		private var subQueries = listOf<QueryBuilder<*>>()

		fun addSubQuery(query: QueryBuilder<*>) {
			subQueries = subQueries + query
		}

		fun removeSubQuery(query: QueryBuilder<*>) {
			subQueries = subQueries - query
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