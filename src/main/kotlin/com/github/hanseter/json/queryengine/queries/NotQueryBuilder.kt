package com.github.hanseter.json.queryengine.queries

import com.github.hanseter.json.queryengine.Query
import com.github.hanseter.json.queryengine.QueryBuilder
import com.github.hanseter.json.queryengine.QueryableData


class NotQueryBuilder(private var negatedQuery: QueryBuilder? = null) :
    QueryBuilder {

    fun setNegatedQuery(query: QueryBuilder): NotQueryBuilder {
        negatedQuery = query
        return this
    }

    override fun isComplete(): Boolean = negatedQuery?.isComplete() ?: false

    override fun build(): NotQuery? = negatedQuery?.build()?.let { NotQuery(it) }

    class NotQuery(private val queryToNegate: Query) : Query {
        override fun matches(data: QueryableData): Boolean = !queryToNegate.matches(data)
    }
}