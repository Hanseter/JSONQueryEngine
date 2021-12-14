package com.github.hanseter.json.queryengine.queries

import com.github.hanseter.json.queryengine.Query


class BetweenQueryBuilder : AttributeQueryBuilder<BetweenQueryBuilder>() {

    fun withBounds(lowerBound: String, upperBound: String): BetweenQueryBuilder {
        val range = lowerBound..upperBound
        this.queryCreator = { path ->
            Query { data ->
                val value = path.getString(data.data) ?: return@Query false
                value in range
            }
        }
        return this
    }

    fun withBounds(lowerBound: Number, upperBound: Number): BetweenQueryBuilder {
        val range = lowerBound.toDouble()..upperBound.toDouble()
        this.queryCreator = { path ->
            Query { data ->
                val value = path.getNumber(data.data) ?: return@Query false
                value.toDouble() in range
            }
        }
        return this
    }

}