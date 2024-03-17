package io.github.hanseter.json.queryengine.queries

import io.github.hanseter.json.queryengine.Query
import io.github.hanseter.json.queryengine.QueryableData


class AndQueryBuilder : QueryWithSubQueriesBuilder<AndQueryBuilder>({ AndQuery(it) }) {

    class AndQuery(private val queries: List<Query>) : Query {
        override fun matches(data: QueryableData): Boolean = queries.all { it.matches(data) }
    }
}