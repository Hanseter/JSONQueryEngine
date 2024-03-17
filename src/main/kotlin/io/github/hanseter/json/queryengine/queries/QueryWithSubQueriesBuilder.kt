package io.github.hanseter.json.queryengine.queries

import io.github.hanseter.json.queryengine.AttributePath
import io.github.hanseter.json.queryengine.Query
import io.github.hanseter.json.queryengine.QueryBuilder

abstract class QueryWithSubQueriesBuilder<T>(
    private val queryCreator: ((List<Query>) -> Query)
) : QueryBuilder {

    var subQueries = listOf<QueryBuilder>()
        private set

    fun addSubQuery(query: QueryBuilder): T {
        subQueries = subQueries + query
        return this as T
    }

    fun removeSubQuery(query: QueryBuilder): T {
        subQueries = subQueries - query
        return this as T
    }

    fun setSubQueries(subQueries: List<QueryBuilder>): T {
        this.subQueries = subQueries.toList()
        return this as T
    }

    override fun isComplete(): Boolean =
        subQueries.isNotEmpty() && subQueries.all { it.isComplete() }

    override fun build(): Query? {
        val queries = subQueries.map { it.build() }
        if (queries.any { it == null }) return null
        return queryCreator(queries as List<Query>)
    }
}