package com.github.hanseter.json.queryengine.queries

import com.github.hanseter.json.queryengine.Query

class GreaterThanQueryBuilder : AttributeQueryBuilder<GreaterThanQueryBuilder>() {

    fun withLowerBound(lowerBound: String): GreaterThanQueryBuilder {
        this.queryCreator = { path ->
            Query { data ->
                val value = path.getString(data.data) ?: return@Query false
                value >= lowerBound
            }
        }
        return this
    }

    fun withLowerBound(lowerBound: Number): GreaterThanQueryBuilder {
        this.queryCreator = { path ->
            Query { data ->
                val value = path.getNumber(data.data) ?: return@Query false
                value.toDouble() >= lowerBound.toDouble()
            }
        }
        return this
    }
}