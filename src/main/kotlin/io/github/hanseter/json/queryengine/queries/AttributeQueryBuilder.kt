package io.github.hanseter.json.queryengine.queries

import io.github.hanseter.json.queryengine.AttributePath
import io.github.hanseter.json.queryengine.Query
import io.github.hanseter.json.queryengine.QueryBuilder

abstract class AttributeQueryBuilder<T> : QueryBuilder {

    var attributePath: AttributePath? = null
        private set
    protected var queryCreator: ((AttributePath) -> Query)? = null

    fun withAttributePath(path: String): T {
        this.attributePath = if (path.isEmpty()) null
        else AttributePath(path)
        return this as T
    }

    fun withAttributePath(path: List<String>): T {
        this.attributePath = if (path.isEmpty()) null
        else AttributePath(path)
        return this as T
    }

    fun withAttributePath(path: AttributePath?): T {
        this.attributePath = path
        return this as T
    }

    override fun isComplete(): Boolean = attributePath != null
            && queryCreator != null

    override fun build(): Query? {
        val attributePath = this.attributePath ?: return null
        val queryCreator = this.queryCreator ?: return null
        return queryCreator(attributePath)
    }
}