package com.github.hanseter.json.queryengine.queries

import com.github.hanseter.json.queryengine.Query

class LessThanQueryBuilder : AttributeQueryBuilder<LessThanQueryBuilder>() {

    fun withUpperBound(upperBound: String):LessThanQueryBuilder {
        this.queryCreator = { path ->
            Query { data ->
                val value = path.getString(data.data) ?: return@Query false
                value <= upperBound
            }
        }
        return this
    }

    fun withUpperBound(upperBound: Number):LessThanQueryBuilder {
        this.queryCreator = { path ->
            Query { data ->
                val value = path.getNumber(data.data) ?: return@Query false
                value.toDouble() <= upperBound.toDouble()
            }
        }
        return this
    }
}