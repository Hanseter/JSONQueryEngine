package com.github.hanseter.json.queryengine

class NotQuery(private val queryToNegate: Query) : Query {

    override fun matches(data: QueryableData): Boolean = !queryToNegate.matches(data)

    class NotQueryBuilder(private var negatedQuery: QueryBuilder<*>? = null) :
        QueryBuilder<NotQuery> {

        fun setNegatedQuery(query: QueryBuilder<*>) {
            negatedQuery = query
        }

        override fun isComplete(): Boolean = negatedQuery?.isComplete() ?: false

        override fun build(): NotQuery? = negatedQuery?.build()?.let { NotQuery(it) }
    }
}