package com.github.hanseter.json.queryengine

class AndQuery(private val queries: List<Query>) : Query {
    override fun matches(data: QueryableData): Boolean = queries.all { it.matches(data) }

    class AndQueryBuilder : QueryBuilder<AndQuery> {
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

        override fun isComplete(): Boolean =
            subQueries.isNotEmpty() && subQueries.all { it.isComplete() }

        override fun build(): AndQuery? {
            val queries = subQueries.map { it.build() }
            if (queries.any { it == null }) return null
            return AndQuery(queries.requireNoNulls())
        }
    }
}