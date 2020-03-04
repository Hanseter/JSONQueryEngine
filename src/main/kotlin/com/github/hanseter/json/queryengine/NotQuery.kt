package com.github.hanseter.json.queryengine

import org.json.JSONObject

class NotQuery(val queryToNegate: Query) : Query {

	override fun matches(data: QuerieableData): Boolean = !queryToNegate.matches(data)

	public class NotQueryBuilder(private var negatedQuery: QueryBuilder<*>? = null) : QueryBuilder<NotQuery> {

		fun setNegatedQuery(query: QueryBuilder<*>) {
			negatedQuery = query
		}

		override fun isComplete(): Boolean = negatedQuery?.isComplete() ?: false

		override fun build(): NotQuery? = negatedQuery?.build()?.let { NotQuery(it) }
	}
}