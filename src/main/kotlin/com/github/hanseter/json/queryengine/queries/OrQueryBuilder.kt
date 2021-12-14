package com.github.hanseter.json.queryengine.queries

import com.github.hanseter.json.queryengine.Query
import com.github.hanseter.json.queryengine.QueryBuilder
import com.github.hanseter.json.queryengine.QueryableData


class OrQueryBuilder : QueryWithSubQueriesBuilder<OrQueryBuilder>({ OrQuery(it) }) {

    class OrQuery(private val queries: List<Query>) : Query {
        override fun matches(data: QueryableData): Boolean = queries.any { it.matches(data) }
    }

}