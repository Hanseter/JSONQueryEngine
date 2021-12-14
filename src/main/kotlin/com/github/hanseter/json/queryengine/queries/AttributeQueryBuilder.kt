package com.github.hanseter.json.queryengine.queries

import com.github.hanseter.json.queryengine.AttributePath
import com.github.hanseter.json.queryengine.Query
import com.github.hanseter.json.queryengine.QueryBuilder

abstract class AttributeQueryBuilder<T> : QueryBuilder {

    var attributePath: AttributePath? = null
        private set
    protected var queryCreator: ((AttributePath) -> Query)? = null

    fun withAttributePath(path: String) : T {
        if (path.isEmpty()) {
            this.attributePath = null
        } else {
            this.attributePath = AttributePath(path)
        }
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